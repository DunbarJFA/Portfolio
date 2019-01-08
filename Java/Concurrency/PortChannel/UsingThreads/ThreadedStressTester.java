import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ThreadedStressTester{
    public static void main (String[] args){
        if(args.length == 3){
            //get personal IP address
            String ipAddy = args[0];
            int port = Integer.parseInt(args[1]);
            int id = Integer.parseInt(args[2]);
            //build endpoint object
            ChannelEndpointByThr endpoint;
            endpoint = new ChannelEndpointByThr(port, ipAddy, id);
            //establish connection
            try {
                int start = (int)System.currentTimeMillis();
                endpoint.setup();
                for (int i = 0; i <= 600; i++){
                    endpoint.send("Test from #" + id + ":" + i);
                    endpoint.receive();
                }
                endpoint.send("Close");
                
                int stop = (int)System.currentTimeMillis();
                int duration = stop - start;
                System.out.println("Average Response time: " + duration/600);     
                
                endpoint.close();
            }catch (IOException ioe){
                System.out.println("Error: Connection problem!");
                ioe.printStackTrace();
                System.exit(-1);
            }catch(InterruptedException ie){
                System.out.println("Error: Thread Interrupted!");
                System.exit(-1);
            }
        }else{
            System.out.println("Missing Arguments. IP, Port Number, and ID number Required.");
        }
    }
}