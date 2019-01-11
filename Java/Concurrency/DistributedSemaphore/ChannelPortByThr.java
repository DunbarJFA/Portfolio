package dist_sem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
*Threaded Implementation of ChannelPort and ChannelPort4Impl interfaces.
*
*Establishes a ServerSocket for incoming connections. Accepts incoming 
*Client connections. Attaches Socket to handle outgoing messages and 
*Listener object to ServerSocket to handle incoming messages.
*
*Upon receipt of a Serializable message, Listeners enqueue the message
*which is then broadcast to all connected Clients.
*
*Closes all connections once Clients signal completed message passing.  
*
*@author Jerod Dunbar
*/

public class ChannelPortByThr implements ChannelPort, ChannelPort4Impl{
    int portID;
    int endpointCount;
    ArrayList<Listener> listeners;
    ConcurrentLinkedQueue<Serializable> queue;
    ObjectOutputStream[] outbox;
    ObjectInputStream[] inbox;
    ArrayList<NodeInfo> nodes;
    int clientsFinished = 0;
    int messageFlag = 0;
    Boolean initiator = false;

    
    public ChannelPortByThr(int portID, int endpointCount) {
        this.portID = portID;
        this.endpointCount = endpointCount;
        listeners = new ArrayList<>(endpointCount);
        queue = new ConcurrentLinkedQueue<Serializable>();
        outbox = new ObjectOutputStream[endpointCount];
        nodes = new ArrayList<>();
    }

    public void setup(){
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(portID);
        }catch(IOException ioe){
            System.out.println("Error establishing connection!");
            ioe.printStackTrace();
            System.exit(-1);
        }
        for(int i = 0; i < endpointCount; i++){
            try {
                System.out.println("Waiting for Client...");
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream inFlow = new ObjectInputStream(clientSocket.getInputStream());
                outbox[i] = new ObjectOutputStream(clientSocket.getOutputStream());
                listeners.add(new Listener(i, inFlow, this));
                if(initiator){
                    int id = inFlow.readInt();
                    int port = inFlow.readInt();
                    String ip = clientSocket.getInetAddress().getHostAddress();
                    nodes.add(new NodeInfo(id,port,ip));
                    System.out.println("Node " + id + " Registered!");
                }
            } catch (IOException ioe) {
                System.out.println("Error Establishing Connection in SETUP!");
            }
        }
        initiator = false;
        System.out.println("All Connections Established!");
    }
    
    public void startListeners(){
        for(Listener listener : listeners){
            listener.start();
        }
        System.out.println("Listening for Messages!");
    }
    @Override
    public synchronized void receive(){
        while(true){
            //wait for message
            while (queue.isEmpty()) {
                messageFlag = 0; 
		    	try {
		    		Thread.sleep(500); 
		    	} catch (InterruptedException ire) {
                    System.out.println("Error in Server Receive!");
		    		ire.printStackTrace();
		    	}
		    } 
            if(!queue.isEmpty()){
                //signal message in queue
                messageFlag = 1;
            }
        }
    }
    
    @Override
    public synchronized void broadcast(Serializable message) {
        System.out.println("BROADCASTING");
        
        for(ObjectOutputStream pw: outbox){    
            try {
                pw.writeObject(message);
                pw.flush();
            } catch (IOException e) {
                System.out.println("Error in Broadcast!");
            }
        }
    }
    
    @Override
    public boolean empty() {
        if (queue.isEmpty()){
            return true;
        }else{    
            return false;
        }
    }
    
    @Override
    public synchronized void enqueue(Serializable message) {
        //add message to queue for broadcasting
        queue.offer(message);
        notifyAll();
    }

    public void finished(){
        clientsFinished++;
        if(clientsFinished >= endpointCount){
            System.out.println("All Clients Finished. Killing Listeners.");
            enqueue("Close");
            for (Listener listener : listeners){
                listener.kill();
            }
            System.out.println("Server Shutting Down.");
        }
    }

    public ConcurrentLinkedQueue<Serializable> getQueue(){
        return queue;
    }

    public int getMessageFlag(){
        return messageFlag;
    }

    public ArrayList<NodeInfo> getNodes(){
        return nodes;
    }

    public void setInitiator(boolean mode){
        initiator = mode;
    }

    public void addListener(Listener listener){
        listeners.add(listener);
    }

}