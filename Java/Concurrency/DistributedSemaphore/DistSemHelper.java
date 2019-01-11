package dist_sem;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;

import dist_sem.ChannelEndpointByThr;
import dist_sem.ChannelPortByThr;
import dist_sem.Listener;
import dist_sem.NodeInfo;

public class DistSemHelper{
    ChannelPortByThr server;
    ChannelEndpointByThr myEndpoint;
    ArrayList<ChannelEndpointByThr> nodeEndpoints;
    
    int semaphore;
    int timestamp;
    int theWatermark;
    int[] watermarks;
    
    int myID;
    int myPort;
    String myIP;
    String initiatorIP;
    int initiatorPort;
    
    ServerSocket serverSocket;
    ObjectOutputStream output;
    ObjectInputStream input;
    ArrayList<NodeInfo> nodes;
    
    ObjectInputStream fromUser;
    ObjectOutputStream toUser;
    
    ConcurrentLinkedQueue<Serializable> inbox;
    ArrayList<String[]> vQueue;
    ArrayList<String[]> pQueue;
    ArrayList<String[]> removeQueueP;
    ArrayList<String[]> removeQueueV;
    
    public DistSemHelper(int id, int port, String targetIP, int targetPort){
        myID = id;
        myPort = port;
        
        initiatorIP = targetIP;
        initiatorPort = targetPort;
        
        semaphore = 1;
        timestamp = 0;

        vQueue = new ArrayList<>();
        pQueue = new ArrayList<>();
        removeQueueP = new ArrayList<>();
        removeQueueV = new ArrayList<>();
    }
    
    public static void main(String[] args) {
        DistSemHelper sem = null;
        if(args.length == 4){
            sem = new DistSemHelper(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
        }else{
            System.out.println("Missing Arguments. Requires ID number, the  port number that this helper listens to, Initiator‘s IP address, and Initiator‘s port number");
            System.exit(0);
        }
        System.out.println("Initiating...");
        try{    
            sem.initiate();
        }catch(Exception e){
            e.printStackTrace();
            try{
                Thread.sleep(1000);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
            System.out.println("Try Again...");
        }
        System.out.println("Creating Network Ring...");
        sem.formNetworkRing();
        System.out.println("Network Established!");
        System.out.println("Waiting for user connection...");
        sem.acceptUser();
        System.out.println("User connected!");
        System.out.println("Executing...");
        sem.execute();
        System.out.println("Connection Terminated. Shutting Down...");
    }
    
    public void initiate(){
        try{
            Socket socket = new Socket(initiatorIP, initiatorPort);
            output = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Got Out!");
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Got In!");
            output.writeInt(myID);
            output.flush();
            output.writeInt(myPort);
            output.flush();
            System.out.println("My Data sent!");
        }catch(IOException ioe){
            System.out.println("Error Establishing Connections");
        }
        while (true) {
            try{
                String ack = (String) input.readObject();
                if(ack.equals("ACK")){
                    nodes = (ArrayList) input.readObject();
                    System.out.println("Got NodeInfo Array! Nodes: " + nodes.size());
                    break;
                }
                Thread.sleep(2000);
            }catch(InterruptedException ie){
                System.out.println("Thread Error in the initiate method!");
            }catch(IOException ioe){
                System.out.println("IO Error in the initiate method!");
                ioe.printStackTrace();
                System.exit(-1);
            }catch(ClassNotFoundException cnfe){
                System.out.println("Error building NodeInfo Array in initiate method!");
                cnfe.printStackTrace();
                System.exit(-1);
            }
        }
        System.out.println("Completed Initiation!");
    }
    
    public void nodeBroadcast(Serializable message){
        for (ChannelEndpointByThr ep : nodeEndpoints){
            ep.send(message);
        }
        server.enqueue(message);
    }
    
    public void execute(){
        //signal readiness to user
        go();
        //setup watermarks array
        watermarks = new int[nodeEndpoints.size()+1];
        for (int i = 0; i < nodeEndpoints.size()+1; i++){
            watermarks[i] = 0;
        }
        theWatermark = 0;
        inbox = new ConcurrentLinkedQueue<>();
        while(true){
            inbox = server.getQueue();
            //parse messages
            if(!inbox.isEmpty()){
                String message = (String) inbox.poll();
                String[] messageParts = message.split(",");
              
                //adjust timestamp for received message
                if(Integer.parseInt(messageParts[2]) > timestamp){
                    timestamp = Integer.parseInt(messageParts[2]) + 1;
                }
                timestamp+=1;
                //the type of message being sent
                String type = "";
                String reply;
                //respond:
                if (messageParts[1].contains("POP")){
                    type = "ACK";
                    reply = myID + "," + type + "," + timestamp;
                    ////broadcast ACK + timestamp
                    nodeBroadcast(reply);
                    timestamp+=1;
                    pQueue.add(messageParts);
                    ////update watermarks
                    if(Integer.parseInt(messageParts[2]) > watermarks[Integer.parseInt(messageParts[0])]){
                        watermarks[Integer.parseInt(messageParts[0])] = Integer.parseInt(messageParts[2]);
                    }
                    for (int i : watermarks){
                        if(i < theWatermark){
                            theWatermark = i;
                            System.out.println("New Watermark: " + theWatermark);
                        }
                    }
                }else if (messageParts[1].contains("VOP")){
                    ////broadcast ACK + timestamp
                    type = "ACK";
                    reply = myID + "," + type + "," + timestamp;
                    nodeBroadcast(reply);
                    timestamp+=1;
                    vQueue.add(messageParts);
                    ////update watermarks
                    if(Integer.parseInt(messageParts[2]) > watermarks[Integer.parseInt(messageParts[0])]){
                        watermarks[Integer.parseInt(messageParts[0])] = Integer.parseInt(messageParts[2]);
                    }
                    for (int i : watermarks){
                        if(i < theWatermark){
                            theWatermark = i;
                            System.out.println("New Watermark: " + theWatermark);
                        }
                    }
                }else if (messageParts[1].contains("ACK")){
                    ////update watermarks
                    if(Integer.parseInt(messageParts[2]) > watermarks[Integer.parseInt(messageParts[0])]){
                        watermarks[Integer.parseInt(messageParts[0])] = Integer.parseInt(messageParts[2]);
                    }
                    for (int i : watermarks){
                        if(i < theWatermark){
                            theWatermark = i;
                            System.out.println("New Watermark: " + theWatermark);
                        }
                    }
                    //check for full acknowledgement
                    System.out.println("Removing Fully Acknowledged Messages...");
                    System.out.println("Number of Vs: " + vQueue.size());
                    System.out.println("Number of Ps: " + pQueue.size());
                    for (String[] s : vQueue){
                        if(Integer.parseInt(s[2]) <= theWatermark){
                            semaphore = semaphore+1;
                            removeQueueV.add(s);
                        }
                    }
                    for (String[] s : removeQueueV){
                        vQueue.remove(s);
                    }
                    removeQueueV.clear();
                    for(String[] s: pQueue){
                            //if possible, decrement semaphore
                        if(semaphore > 0){
                            semaphore = semaphore - 1;
                            if(Integer.parseInt(s[0]) == myID){
                                go();
                            }
                            removeQueueP.add(s);
                            timestamp+=1;
                        }
                    }
                    for(String[] s : removeQueueP){
                        pQueue.remove(s);
                    }
                    removeQueueP.clear();
                    System.out.println("Remaining Messages: ");
                    for (String[] s : vQueue){
                        System.out.println("V: " + s[0] + s[1] + s[2]);
                    }
                    for (String[] s : pQueue){
                        System.out.println("P: " + s[0] + s[1] + s[2]);
                    }
                
                }else if (messageParts[1].contains("reqV")){
                    type = "VOP";
                    reply = myID + "," + type + "," + timestamp;
                    ////Broadcast VOP + timestamp
                    nodeBroadcast(reply);
                    timestamp+=1;
                }else if (messageParts[1].contains("reqP")){
                    type = "POP";
                    reply = myID + "," + type + "," + timestamp;
                    ////Broadcast POP + timestamp
                    nodeBroadcast(reply);
                    timestamp+=1;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
    
    public void acceptUser(){
        try {
            ServerSocket servSoc = new ServerSocket(myPort+1);
            Socket userSocket = servSoc.accept();
            fromUser = new ObjectInputStream(userSocket.getInputStream());
            System.out.println("Got In!");
            toUser = new ObjectOutputStream(userSocket.getOutputStream());
            System.out.println("Got Out!");
            //prepare endpoint for user
            myEndpoint = new ChannelEndpointByThr(myPort, userSocket.getInetAddress().getHostAddress(), myID);
            int userID = fromUser.readInt();
            Listener userListener = new Listener(userID, fromUser, server);
            userListener.start();
            server.addListener(userListener);
        } catch (IOException ioe) {
            System.out.println("Error Establishing Connection to User!");
            ioe.printStackTrace();
            System.exit(-1);
        }
    }
    
    public void go(){
        String go = "GO";
        try{
            toUser.writeUTF(go);
            toUser.flush();
        }catch(IOException ioe){
            System.out.println("Error in Go!");
            ioe.printStackTrace();
        }
    }
    
    public ArrayList<NodeInfo> getNodes(){
        return nodes;
    }
    
    public void setServer(){
        server = new ChannelPortByThr(myPort, nodes.size()-1);
        server.setup();
        server.startListeners();
    }
    
    public int getID(){
        return myID;
    }
    
    public void formNetworkRing(){
        nodeEndpoints = new ArrayList<>();
        for (int i = 0; i < nodes.size();i++){
            if(myID == i){
                setServer();
            }else{
                for(NodeInfo nf : nodes){
                    if(nf.getNodeID() == i){
                        ChannelEndpointByThr eP = new ChannelEndpointByThr(nf.getNodePort(), nf.getNodeIP(), nf.getNodeID());
                        try {
                            eP.setup();
                        } catch (Exception e) {
                            System.out.println("Error Setting up Endpoints!");
                            e.printStackTrace();
                        }
                        System.out.println("Adding EP to List!");
                        nodeEndpoints.add(eP);
                    }
                }
            }
        }
        //network ring is complete
        System.out.println("Network Established.");
    }
}




//mq
//reveice
//thewatermark