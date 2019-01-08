package dist_sem;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import dist_sem.NodeInfo;

public class Initiator{
	ServerSocket servSock;
	Socket clientSocket;

	int myPort;
	int numNodes;

	String helperIP;
	int incomingID;
	int incomingPort;
	DataInputStream input;
	ObjectOutputStream output;
	ObjectOutputStream[] directory;

	ArrayList<NodeInfo> nodes;

	public Initiator(int numHelpers,int port){
		myPort = port;
		numNodes = numHelpers;
		directory = new ObjectOutputStream[numNodes];
		nodes = new ArrayList<>();
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
		servSock = new ServerSocket(port);
		for(int i = 0; i < numNodes; i++){
			System.out.println("Accepting Connections...");
			while (clientSocket == null) {
				clientSocket = servSock.accept();
			}
			//determine helper IP address
			helperIP = clientSocket.getInetAddress().getHostAddress();
//IF NEEDED: clientSocket.getInetAddress().toString().substring(1);
			//prepare to receive helper info
			input = new DataInputStream(clientSocket.getInputStream());
			//get helper info
			incomingID = input.readInt();
			incomingPort = input.readInt();
			//create and store NodeInfo object
			nodes.add(new NodeInfo(incomingID,incomingPort,helperIP));
			//prepare connection to transfer final NodeInfo list
			directory[i] = new ObjectOutputStream(clientSocket.getOutputStream());
		}
		System.out.println("All Helpers Registered. Broadcasting...");
		//send completed NodeInfo List to all Helpers
		for (ObjectOutputStream out : directory){
			out.writeObject(nodes);
		}
		System.out.println("Initiation complete. Closing Initiator.");
	}


}