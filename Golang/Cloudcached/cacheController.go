/*
*cacheController.go is a program for coordinating Memcached cache instances hosted on 
*Amazon Web Services (AWS) Elastic Cloud Container (EC2) t2.micro instances
*and any external user connecting to the controller machine on port 11211 (the standard 
*port for memcached instances).
*
*The goal of this program is to allow for a seemless replacement of a single, basic 
*Memcached instance with a custom distributed Memcached clone (the cacheController).
*This controller provides basic memcached functionality on a distributed scale:
*
*Six basic Memcached instances are hosted (each in its own EC2 t2.micro instance) in a 
*security group that can only be accessed by the cacheController. The controller accepts 
*connections from the outside world through its own security group and routes Memcached 
*commands to cache instances on demand..
*
*The cache instances are oriented into a hashring in order to accommodate even distribution 
*of items and hotspots. The controller hashes commands and uses the result to select an 
*appropriate cache and backup cache to handle the command.
*
*The ultimate goal of this product is to provide the following guarantees:
*	-Compatibility: 
*	--Implement the complete Memcached wire protocol
*	-Fault-Tolerance: 
*	--Survive without impact on correctness the failure of any one of its constituent memcached servers
*	-Auto-Recovery: 
*	--Node crashes will result in replacement nodes that are automatically brought up to speed.
*	-Elasticity: 
*	--Automatically scales up/down in reponse to demand
*	-Service Spec: 
*	--Provides API calls that allow specifications of starting services (how many initial 
*	nodes, elasticity threshold, performance checks, etc)
*/

package main

import (
	"net"
	"os"
	"strings"
	"bufio"
	"strconv"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/ec2"
	//"github.com/aws/aws-sdk-go/service/cloudwatch"
	"github.com/bradfitz/gomemcache/memcache"
	"github.com/serialx/hashring"
)
/*Global Variables*/
var myIp string
var svc *ec2.EC2
var cacheAddresses []string
var caches []*memcache.Client
var breakdown []string
var ring *hashring.HashRing
var text string

const (
	//Controller Listening
	CONN_PORT = ":11211"
	CONN_TYPE = "tcp"
	//Connecting to EC2 Instances
	AWS_REGION = "us-east-2"
	//Arbitrary Maximum Number of IP Addresses to save from the VPC
	NUM_OF_ADDRESSES = 30
)

/*MAIN*/
func main(){
	getMyIP()
	//query instance IPs on the VPC
	////store these IPs in array
	getInstances()
	//Set Up cloudwatch alarms
	//setCloudwatch()
	//Connect caches into hash ring
	connectCaches()
	//listen on port 11211 and create goroutines to handle each connection
	listen()
}

func getMyIP(){
	addrs, err := net.InterfaceAddrs()

	if err != nil {
		os.Stderr.WriteString("Error: " + err.Error() + "\n")
		os.Exit(1)
	}

	for _, a := range addrs {
		if ipnet, ok := a.(*net.IPNet); ok && !ipnet.IP.IsLoopback() {
			if ipnet.IP.To4() != nil {
				myIp = ipnet.IP.String()
			}
		}
	}
}

func listen(){
	//listen for incoming connections
	listener, err := net.Listen(CONN_TYPE, myIp + CONN_PORT)
	if err != nil {
		println("Error Listening: ", err.Error())
		os.Exit(1)
	}

	for {
		//wait for incoming connection
		connection, err := listener.Accept()
		if err != nil {
			println("Error accepting connection: " + err.Error())
			os.Exit(1)
		}
		//spin up a goroutine to handle the connection
		go handler(connection)
	}
}

func handler(conn net.Conn) {
	defer conn.Close()
	writer := bufio.NewWriter(conn)
	reader := bufio.NewReader(conn)
	myWriter("Connected To Server",writer)
	myWriter("Enter Commands When Ready...",writer)
//From this point, Controller constantly waits for messages from clients
//It deciphers these messages and responds/routes them to caches accordingly
	for{
		text, err := reader.ReadString('\n')
		text = strings.TrimSpace(text)
		if err != nil {
			if strings.Contains(err.Error(), "EOF"){
				break
			}else if !strings.Contains(err.Error(),"connection reset by peer"){
				println("Error Reading (LINE 111): ", err.Error())
			}
		}
		if strings.EqualFold("Disco",text) {
			//End Connection
			println("Client Requesting Disconnect")
			println("Disconnecting")
			break
		}else if strings.EqualFold("Clear",text){
			println("Clearing Caches...")
			//clear all caches
			clear()
			myWriter("Caches Cleared",writer)
		}else if strings.EqualFold("ClearQuiet",text){
			println("Clearing Caches...")
			//clear all caches
			clear()
			//Dont write verification to client
		}else{
			//Assumed that Client is attempting to interact with the Hashring
			//println("Received: ",text)
			parser(text,writer,reader)
		}
	}
}
//Function parses strings sent by the client to determine if any are Memcached commands
func parser(command string, writer *bufio.Writer, reader *bufio.Reader) {
	breakdown = strings.Split(command," ")
	var err error
	var msg string
	var key string
	if len(strings.TrimSpace(breakdown[0])) == 0 || breakdown[0] == "" || breakdown[0] == " "{
		//trash input. Ignore.
		return
	}
	switch true {
//SET
	case strings.EqualFold("set",breakdown[0]):
		//println("Set Command")
		if !(len(breakdown) == 5){
            myWriter("ERROR: Incorrect Command Format",writer)
            break
        }
        //read the value string
        msg, err = reader.ReadString('\n')
        if err != nil && !strings.Contains(err.Error(),"connection reset by peer"){ 
			if !strings.Contains("EOF",err.Error()){
				println("Error Reading (LINE 138): ", err.Error())
			}
		}
		//Create memcache.Item from read-in string
		if len(breakdown) < 2{
			println("Incorrect Command Format")
			break
		}
        item := memcache.Item{Key: breakdown[1], Value: []byte(msg)}
		//Select a cache to hold the item
		//by hashing the key and selecting a desired number of caches
		//chose 2 here for some redundancy
                clients,success := ring.GetNodes(breakdown[1],2)
		if success != true{println("Error Picking Nodes")}
                //attempt to set item to cache
                for _,client := range clients{
					memcache := memcache.New(client)
					err = memcache.Set(&item)
				}
		if err != nil {myWriter(err.Error(),writer)}
                //report success
                myWriter("STORED",writer)
//GET
	case strings.EqualFold("get",breakdown[0]):
		//println("Get Command")
		//If key is forgotten as input
		if (len(breakdown) < 2){
			myWriter("Get Command Missing Key", writer)
			break
		}
		var keys []string
		var item *memcache.Item
		var err error
		for i := 1; i < len(breakdown); i++ {
			keys = append(keys, breakdown[i])
		}
		for _,key := range keys{
			item,err = gets(key)
			if err != nil{
				myWriter(err.Error(),writer)
				break
			}
			myWriter(string(item.Value),writer)
		}
//DELETE
	case strings.EqualFold("delete",breakdown[0]):
		//println("Delete Command")
			if (len(breakdown) < 2){
					myWriter("Delete Command: Missing Item Key", writer)
					break
			}
			//read the key string
			key = breakdown[1]
			//find appropriate cache
			client := caches[0]
			//query result
			err := client.Delete(key)
			//report cache miss
			if err != nil {
					println("Error Deleting: ",err.Error())
					myWriter(err.Error(),writer)
			}
			//report success
			myWriter("Deleted",writer)
//CACHES			
	case strings.EqualFold("caches",breakdown[0]):
		myWriter("Caches: "+ strconv.Itoa(len(cacheAddresses)),writer)
	default:
			println("Not a Valid Command")
			break
	}
}

func myWriter(text string, writer *bufio.Writer){
	_, err := writer.WriteString(text + "\n")
	if err != nil {
		if !strings.Contains(err.Error(),"connection reset by peer"){
			if !strings.Contains(err.Error(), "broken pipe"){
				println("Error Writing: ", err.Error())
			}
		}
	}
	err = writer.Flush()
	if err != nil {
		if !strings.Contains(err.Error(),"connection reset by peer"){
			if !strings.Contains(err.Error(), "broken pipe"){
				println("Error Flushing: ", err.Error())
			}
		}
	}
}

// func setCloudwatch(){
// 	svc := cloudwatch.New(session.Must(session.NewSession(&aws.Config{
// 		Region: aws.String(AWS_REGION),
// 	})))
// 	//Set a rule that starts another pair of instances if our 
// 	//initial pair gets above a baseline level of CPU usage
// 	svc.PutMetricAlarm(&cloudwatch.PutMetricAlarmInput{
// 			AlarmName:"Cache_CPU_Utilization",
// 			ComparisonOperator:ComparisonOperator.GreaterThanThreshold,
// 			EvaluationPeriods:1,
// 			MetricName:"CPUUtilization",
// 			Namespace:"AWS/EC2",
// 			Threshold:10.0,
// 			Period:60,
// 			ActionsEnabled:true,
// 			Statistic:Statistic.Average,
// 			AlarmActionss: []string{
// 				"arn:aws:ec2:us-east-2:311515722348:action/actions/AWS_EC2.i-0105956e386234a6c.Start",
// 				"arn:aws:ec2:us-east-2:311515722348:action/actions/AWS_EC2.i-0a05020fa47a400b7.Start",
// 			}
// 			AlarmDescription:"Alarm for when Server CPU Usage Exceeds 20%",
// 			Dimensions: []cloudwatch.Dimension{
// 				new Dimension{Name:"InstanceID",Value:"i-0f619dbfa8b6a431c"}
// 			},
// 			Unit:StandardUnit.Seconds,
// 		}
// 	)
// };

//Function gathers IP Addresses of any other running EC2 instances on the VPC and stores them
func getInstances(){
	addyDelim := string('"')
	var addresses [NUM_OF_ADDRESSES]string
	i := 0
	//create a new session with AWS in order to make API calls
	svc = ec2.New(session.Must(session.NewSession(&aws.Config{
		//specify the hosting region to narrow the search
		Region: aws.String(AWS_REGION),
	})))
	//Create a Filter to ensure that we only gather addresses for the correct instances
	input := &ec2.DescribeInstancesInput{
		Filters: []*ec2.Filter{
			{
				//filtering out all but those tagged as "Cache"
				Name: aws.String("tag:Type"),
				Values: []*string{
					aws.String("Cache"),
				},
			},
		},
	}
	//Error handling
	result, err := svc.DescribeInstances(input)
	if err != nil {
		println("Error Describing Instances: " + err.Error())
		os.Exit(1)
	}
	//Take the resulting raw instance string data and split it aroung commas for parsing
	resultSplit := strings.Split(result.GoString(), ",")
	//Of all of the new substrings, keep only the ones containing "PrivateIpAddress"
	for _, item := range resultSplit {
		//TESTING INSTANCE DATA
		//println(item)
		if strings.Contains(item, "PrivateIpAddress:"){
			//split these again around colons and store the latter half
			//this contains the IP address string
			raw := strings.Trim(strings.Split(item, ":")[1]," ")
			//trim excess characters and whitespace from the addresses
			addresses[i] = trimAddy(raw, addyDelim)
			i++
		}
	}
	//Remove Duplicates from the address array
	addySlice := addresses[:]
	addySlice = noDupes(addySlice)
	//Store the processed addresses in a global slice
	for _,item := range addySlice {
		item = item + CONN_PORT
		cacheAddresses = append(cacheAddresses,item)
	}
}
//Function removes excess characters and whitespaces from IPs
func trimAddy(raw string, delim string) string{
	//Trim excess brackets
	value := strings.TrimRight(raw," }]")
	//Trim excess whitespace
	value = strings.TrimSpace(value)
	//Trim quotation marks
	return strings.TrimLeft(strings.TrimRight(value, delim),delim)
}
//Function elimininates duplicate string values in a slice by placing unique
//values into a new slice and returning that slice
func noDupes(fat []string) []string{
	var slim [0]string
	var slimSlice []string = slim[:]
	for _, item := range fat {
		if !existsIn(item,slimSlice){
			if strings.Compare(myIp,item) != 0{
				slimSlice = append(slimSlice, item)
			}
		}
	}
	return slimSlice
}
//Function checks whether a string exists in a slice
func existsIn(str string, list []string) bool{
	for _,item := range list {
		if item == str {
			return true
		}
	}
	return false
}
//Takes a slice of IP addresses and formulates them into a hashring
func connectCaches() {
	ring = hashring.New(cacheAddresses)
}

func gets(key string) (*memcache.Item, error){
        //find appropriate caches
        clients,success := ring.GetNodes(key,2)
        if success != true{println("Error Picking Nodes")}
	//query result from primary
        item, err := memcache.New(clients[0]).Get(key)
	//if primary misses, query mirror
        if err != nil {
       		item,err := memcache.New(clients[1]).Get(key)
                //if mirror misses, return cache miss
		if err != nil {
                	return item,err
                }
        	//report success
         	return item,nil
	 }
         //report success
         return item,nil
}
//Deletes all key/value pairs in all caches of the hashring
func clear(){
	var client *memcache.Client
	for _,cache := range cacheAddresses {
		client = memcache.New(cache)
		client.DeleteAll()
	}
}
