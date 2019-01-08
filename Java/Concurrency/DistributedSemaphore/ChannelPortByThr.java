package dist_sem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
    ServerSocket serverSocket;
    Listener[] listeners;
    ConcurrentLinkedQueue<Serializable> inbox;
    PrintWriter[] outbox;
    int clientsFinished = 0;
    int messageFlag = 0;

    
    public ChannelPortByThr(int portID, int endpointCount) {
        this.portID = portID;
        this.endpointCount = endpointCount;
        listeners = new Listener[endpointCount];
        inbox = new ConcurrentLinkedQueue<Serializable>();
        outbox = new PrintWriter[endpointCount];
    }

    public void setup(){
        serverSocket = null;
        try{
            serverSocket = new ServerSocket(portID);
        }catch(IOException ioe){
            System.out.println("Error establishing connection!");
        }
        for(int i = 0; i < endpointCount; i++){
            try {
                System.out.println("Waiting for Client...");
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream inFlow = new ObjectInputStream(clientSocket.getInputStream());
                outbox[i] = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                listeners[i] = new Listener(i, inFlow, this);
            } catch (IOException ioe) {
                System.out.println("Error Establishing Connection in SETUP!");
            }
        }
        System.out.println("All Connections Established!");
    }
    
    public void startListeners(){
        for(Listener listener : listeners){
            listener.start();
        }
        System.out.println("Listening for Messages!");
        receive();
    }
    @Override
    public synchronized void receive(){
        while(true){
            //wait for message
            while (inbox.isEmpty()) {
                messageFlag = 0; 
		    	try {
		    		wait(); 
		    	} catch (InterruptedException ire) {
		    		ire.printStackTrace();
		    	}
		    } 
            if(!inbox.isEmpty()){
                //signal message in queue
                messageFlag = 1;
                
            }
        }
    }
    
    @Override
    public synchronized void broadcast(Serializable message) {
        System.out.println("BROADCASTING");
        
        for(PrintWriter pw: outbox){    
            pw.println(message);
            pw.flush();
        }
    }
    
    @Override
    public boolean empty() {
        if (inbox.isEmpty()){
            return true;
        }else{    
            return false;
        }
    }
    
    @Override
    public synchronized void enqueue(Serializable message) {
        //add message to inbox for broadcasting
        inbox.offer(message);
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
        return inbox;
    }

    public int getMessageFlag(){
        return messageFlag;
    }

}