import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class EndpointMainThr{
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
                endpoint.setup();
                endpoint.send("Black");
                endpoint.receive();
                endpoint.send("Gold");
                endpoint.receive();
                endpoint.send("Super");
                endpoint.receive();
                endpoint.send("Bowl");
                endpoint.receive();
                endpoint.send("Close");     
                endpoint.close();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
             }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }else{
            System.out.println("Missing Arguments. IP, Port Number, and ID number Required.");
        }
    }
}