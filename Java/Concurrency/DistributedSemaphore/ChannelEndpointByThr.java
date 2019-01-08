package dist_sem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/*
*Endpoint process for connecting to an object of type ChannelPortByThr for message passing.
*Connects via Socket to the IP address of the ChannelPortByThr Server. Utilizes the connection
*to send a Serializable message that is expected to be echoed by the target.
*
*@author Jerod Dunbar
*/

public class ChannelEndpointByThr implements ChannelEndpoint{
    int port;//number corresponding to the port through which we'll connect to Server
    String ip;//target ip of Server
    int id; //the personal identifier for this Endpoint
    Socket socket = null;
    ObjectOutputStream outbox;
    BufferedReader inbox;
    String response = "EMPTY";
    String[] splitter = new String[2];

    public ChannelEndpointByThr(int port, String ip, int id){
        this.port = port;
        this.ip = ip;
        this.id = id;
    }
    
    public void setup() throws InterruptedException{
        while(socket == null){
            try{
                //establish socket connection to server
                socket = new Socket(ip, port);
                System.out.println("Socket Established");
                //establish outputstream for message passing to server
                outbox = new ObjectOutputStream(socket.getOutputStream());
                //establish inputstream for receiving messages from server
                inbox = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //setup splitter with filler text to allot while loop in receive()
                splitter[0] = response;

            }catch(UnknownHostException uhe){
                System.err.println("Server Unknown");
            }catch(IOException ioe){
                System.err.println("Connecting...");
            }
            try{
                //if connection fails, wait then try again
                Thread.sleep(1000);
            }catch(InterruptedException ie){
                System.out.println("Thread Interrupted!");
            }
        }
    }

    @Override
    public void send(Serializable message) {
        try {
            message = message.toString().concat("," + id);
            outbox.writeObject(message);
            outbox.flush();
            System.out.println("Sending..." + message.toString());
        }catch (SocketException se){
            System.exit(1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public synchronized void receive() {//wait until string!=null then continue
        try {
            while(inbox.ready() == false){
                Thread.sleep(1000);
            }
        } catch (IOException ioe) {
            //TODO: handle exception
        }catch (InterruptedException ie){
            //TODO: handle exception
        }
        try{
            while(true){
                response = inbox.readLine();
                if(response != null){
                    splitter = response.split(",");
                }
                if(splitter.length == 2){
                    if(Integer.parseInt(splitter[1]) == id){
                        System.out.println("Received Response: " + response);
                        break;
                    }
                }
            }
        }catch(IOException ioe){
            //TODO: handle exception
        }
    }

    public void close() throws IOException { 
		try { 
			System.out.println("Shutting down..."); 
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			socket.close();
			outbox.close();
			inbox.close();
		}
    }
    
    public String getResponse(){
        return response;
    }
}//end of class ChannelEndpointByThr