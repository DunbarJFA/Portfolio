package dist_sem;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;

import dist_sem.ChannelEndpointByThr;
import dist_sem.ChannelPortByThr;
import dist_sem.Listener;
import dist_sem.NodeInfo;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class DistSemHelper{

    ChannelPortByThr server;
    ChannelEndpointByThr myEndpoint;
    ArrayList<ChannelEndpointByThr> nodeEndpoints;

    int semaphore;
    int clock;
    int[] watermarks;

    int myID;
    int myPort;
    String myIP;
    String initiatorIP;
    int initiatorPort;

    ServerSocket serverSocket;
    Socket socket = null;
    DataOutputStream output;
    ObjectInputStream input;
    ArrayList<NodeInfo> nodes;

    ObjectInputStream fromUser;
    ObjectOutputStream toUser;

    ConcurrentLinkedQueue inbox;
    ArrayList<String[]> queue;

    public DistSemHelper(int id, int port, String targetIP, int targetPort){
        myID = id;
        myPort = port;
        System.out.println("MY IP!! : " + InetAddress.getLocalHost().toString());
        myIP = InetAddress.getLocalHost().toString();
        initiatorIP = targetIP;
        initiatorPort = targetPort;

        semaphore = 0;
        clock = 0;
    }

    public static void main(String[] args) {
        DistSemHelper sem;
        if(args.length == 4){
            sem = new DistSemHelper(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
        }else{
            System.out.println("Missing Arguments. Requires ID number, the  port number that this helper listens to Initiator‘s IP address, and Initiator‘s port number");
            System.exit(0);
        }

        sem.initiate();
        System.out.println("Call to method execute() happens here!!");
    }
    
    public void initiate(){
        //send id and listening port number to Initiator ip
        while (socket == null) {
            //establish socket connection to the initiator
            socket = new Socket(initiatorIP, initiatorPort);
            output = new DataOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        }
        //send connection info to initiator 
        output.writeInt(myID);
        output.writeInt(myPort);

        //receive NodeInfo ArrayList from initiator
        while (nodes == null) {
            nodes = (ArrayList<NodeInfo>)input.readObject();
        }
        //prepare endpoints for each expected helper neighbor 
        collectEndpoints(nodes);
        watermarks = new int[nodeEndpoints.size()];
        //create a ChannelPort that handles all helper endpoints
        server = new ChannelPortByThr(myPort, nodeEndpoints.size());
        if (myID%2 == 0) {
            //accept connections
            server.setup();
            //then connect
            for (ChannelEndpointByThr ep : nodeEndpoints){
                ep.setup();
            }
        } else {
            //connect
            for (ChannelEndpointByThr ep : nodeEndpoints){
                ep.setup();
            }
            //then accept connections
            server.setup();            
        }
        //with all endpoints accepted, we can start to listen for incoming messages
        server.startListeners();

        //with the network established, all we need is a user to take advantage of it

    }

    public void collectEndpoints(ArrayList<NodeInfo> nodes){
        for (NodeInfo nf : nodes){
            nodeEndpoints.add(new ChannelEndpointByThr(nf.getNodePort(), nf.getNodeIP(), nf.getNodeID()));
        }
        myEndpoint = new ChannelEndpointByThr(myPort, myIP, myID);
        nodeEndpoints.add(myEndpoint);
    }

    public void nodeBroadcast(Serializable message){
        for (ChannelEndpointByThr ep : nodeEndpoints){
            ep.send(message);
        }
    }

    public void execute(){
        while (true) {
            //implements algorithm at 9.13
            //while message queue is empty, check for messages
            if(server.getMessageFlag() == 1){
                inbox = server.getQueue();
                //remove message from the head of queue using poll()
                Serializable envelope = inbox.poll();
                if(message.equals("Close")){
                    break;
                }else{
                    System.out.println("Message Received: " + envelope.toString());
                    String[] message = envelope.toString().split(",");
                    int id = Integer.parseInt(message[0]);
                    int ts = Integer.parseInt(message[2]);
                    
                    if (message[1].equals("VOP")){
                        if (watermarks[id] < ts){
                            watermarks[id] = ts;
                        }
                        queue.add(message);
                        String reply = myID + ",ACK," + clock;
                        nodeBroadcast(reply);
                    }else if(message[1].equals("POP")){
                        if (watermarks[id] < ts){
                            watermarks[id] = ts;
                        }
                        queue.add(message);
                        String reply = myID + ",ACK," + clock;
                        nodeBroadcast(reply);
                    }else if(message[1].equals("ACK")){
                        if (watermarks[id] < ts){
                            watermarks[id] = ts;
                        }
                        
                    }else if(message[1].equals("reqP")){
                        String reply = myID + ",POP," + clock;
                        nodeBroadcast(reply);
                    }else if(message[1].equals("reqV")){
                        String reply = myID + ",VOP," + clock;
                        nodeBroadcast(reply);
                    }
                }
            }
            
        }
    }

    public void acceptUser(){
        serverSocket = null;
        try{
            serverSocket = new ServerSocket(portID);
        }catch(IOException ioe){
            System.out.println("Error establishing connection!");
        }
        try {
            System.out.println("Waiting for User...");
            Socket userSocket = serverSocket.accept();
            fromUser = new ObjectInputStream(userSocket.getInputStream());
            toUser = new ObjectOutputStream(userSocket.getOutputStream());
            } catch (IOException ioe) {
                System.out.println("Error Establishing Connection to User!");
            }
        System.out.println("Connected to User!");
    }
}