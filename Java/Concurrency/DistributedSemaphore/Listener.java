package dist_sem;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.SocketException;
/*
*Object for monitoring ServerSocket for Serializable messages from a Client process attached to an
*object of type ChannelPortByThr (Server). Waits until message is received, then notifies the
*Server of the incoming message and enqueues it for the server to process.
*  
*Client process must send a String "Close" to the server in order for the listener to stop.
*
*@author Jerod Dunbar
*/
public class Listener extends Thread{
   int id;
   ObjectInputStream inbox;
   ChannelPortByThr hub;
   final int ERRNO = 100;
   

    public Listener(int id, ObjectInputStream in, ChannelPortByThr cp){
        this.id = id + 1;   
        inbox = in;
        hub = cp;
    }

   public void run(){
       String msg;
       int errCount = 0;

       while(inbox != null){
            try {
                msg = (String) inbox.readUTF();
                if(msg.equals("Close")){
                    System.out.println("Listener " + id + " Client Signaled Close.");
                    hub.finished();
                }
                else{
                    System.out.println("Received: " + msg);
                    //add message to server inbox
                    hub.enqueue(msg);
                }
            }catch(SocketException se){
               System.err.println(se);
               errCount++;
               if (errCount > ERRNO) System.exit(0);
            }catch(EOFException eofe){
                errCount++;
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    public void kill(){
        System.out.println("Listener " + id + " Shutting Down...");
        try{    
            inbox.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        inbox = null;
    }
}