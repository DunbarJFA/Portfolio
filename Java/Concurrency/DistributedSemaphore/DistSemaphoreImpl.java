package dist_sem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import dist_sem.ChannelEndpointByThr;
import dist_sem.DistSem;

public class DistSemaphoreImpl implements DistSem{

    int helperID;
    String helperIP;
    int helperPort;

    Socket socket;

    ObjectOutputStream outbox;
    ObjectInputStream inbox;

    int timestamp;

    
    public DistSemaphoreImpl(int nodeID, String nodeIP, int nodePort){
        helperID = nodeID;
        helperIP = nodeIP;
        helperPort = nodePort;
        timestamp = 0;
    }

    public void connectToHelper(){
        try{
            Socket socket = new Socket(helperIP, helperPort);
            outbox = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Got Out!");
            inbox = new ObjectInputStream(socket.getInputStream());
            System.out.println("Got In!");
            outbox.writeInt(helperID);
            outbox.flush();
            String ack = inbox.readUTF();
            if(ack.equals("GO")){
                try{
                    Thread.sleep(500);
                }catch(InterruptedException ie){
                    System.out.println("Error in connectToHelper!");
                }
                System.out.println("Connection Complete!");
            }else{
                System.out.println("Connection Failed.");
                System.exit(-1);
            }
        }catch(UnknownHostException uhe){
            System.out.println("Error Finding Host while Connecting to Helper!");
            System.exit(-1);
        }catch(IOException ioe){
            System.out.println("IO Error while Connecting to Helper!");
            System.exit(-1);
        }
    }

    @Override
    public void P(){
        try{
            String message = helperID + "," + "reqP" + "," + timestamp;
            //send reqP to helper to pass on 
            outbox.writeUTF(message);
            outbox.flush();
            timestamp=+1;
            //waits until semaphore is positive, then decrements
            String go = null;
            while(true){
                System.out.println("Waiting...");
                go = inbox.readUTF();
                timestamp+=1;
                if(go!=null){
                    if(go.equals("GO")){
                        break;
                    }
                }
            }
        }catch(IOException ioe){
            System.out.println("IO Error in P!");
        }
    }

    @Override
    public void V(){
        try{
            String message = helperID + ",reqV," + timestamp;
            //send reqV to helper to pass on
            outbox.writeUTF(message);
            outbox.flush();
            timestamp+=1;
        }catch(IOException ioe){
            System.out.println("IO Error in V!");
        }
    }
}