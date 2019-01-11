package dist_sem;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import dist_sem.NodeInfo;

public class Initiator{

	ChannelPortByThr server;
	ConcurrentLinkedQueue<Serializable> queue;

	int myPort;
	int numNodes;

	String helperIP;
	int incomingID;
	int incomingPort;
	DataInputStream input;
	DataInputStream[] directoryIn;
	ObjectOutputStream output;
	ObjectOutputStream[] directoryOut;
	String[] ips;

	ArrayList<dist_sem.NodeInfo> nodes;
	ArrayList<Serializable> data;

	public Initiator(int numHelpers,int port){
		myPort = port;
		numNodes = numHelpers;
	}

	public static void main(String[] args) {
		if(args.length == 2){
			Initiator init = new Initiator(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			init.initiate();
		}else{
			System.out.println("Missing Arguments: requires number of helper nodes and listening port number");
			System.exit(0);
		}
	}

	public void initiate(){
		data = new ArrayList<>();
		server = new ChannelPortByThr(myPort, numNodes);
		server.setInitiator(true);
		server.setup();
		//create NodeInfo objects
		nodes = server.getNodes();
		System.out.println("Got NodeInfo!");
		//prep helpers to receive
		server.broadcast("ACK");
		//broadcast NodeInfo list
		server.broadcast(nodes);
		System.out.println("All Helpers Registered.");		
		System.out.println("Initiation complete. Closing Initiator...");
	}
}