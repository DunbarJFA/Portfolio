package dist_sem;

import java.net.Socket;

import dist_sem.ChannelEndpointByThr;
import dist_sem.DistSem;

public class DistSemaphoreImpl implements DistSem{

    int helperID;
    String helperIP;
    int helperPort;
    ChannelEndpointByThr endpoint;

    Socket socket;

    
    public DistSemaphoreImpl(int nodeID, String nodeIP, int nodePort){
        helperID = nodeID;
        helperIP = nodeIP;
        helperPort = nodePort;
    }

    public void connectToHelper(){
        endpoint = new ChannelEndpointByThr(helperPort, helperIP, helperID);
        try{
            endpoint.setup();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

    @Override
    public void P(){
        String message = "reqP";
        //send reqP to helper to pass on 
        endpoint.send(message);
        //waits until semaphore is positive, then decrements
    }

    @Override
    public void V(){
        String message = "reqV";
        //send reqV to helper to pass on
        endpoint.send(message);
        //increment the semaphore
    }
}