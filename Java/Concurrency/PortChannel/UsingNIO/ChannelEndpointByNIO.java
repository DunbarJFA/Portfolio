import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChannelEndpointByNIO implements ChannelEndpoint{
    SocketChannel channel;
    int id;
    String targetAddy;
    int port;
    int size;
    byte[] outgoingBytes;
    int messageLength;
    ByteBuffer outbox;
    byte[] incomingBytes;
    ByteBuffer inbox;

    public ChannelEndpointByNIO(int id, String targetAddy, int port, int size){
        this.id = id;
        this.targetAddy = targetAddy;
        this.port = port;
        this.size = size;
    }

    public void setup(){
        try{
            //initialize channel connection
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(targetAddy,port));
            //sleep until connection completed
            while(!channel.finishConnect() ){			// make sure connection is established 
                Thread.sleep(100);  
            }
        } catch(IOException ioe){
            System.out.println("IOException during setup!");
            System.exit(-1);
        } catch(InterruptedException ie){
            System.out.println("Thread Interrupted during setup!");
        }
        //initialize sending and receiving buffers
        outbox = ByteBuffer.allocate(size);
        inbox = ByteBuffer.allocate(size);
    }
    
    @Override
    public void send(Serializable message) {
        //parse message into bytes for transmission
        outgoingBytes = message.toString().getBytes();
        //first four bytes of the transmission will indicate the size of the message sent
        messageLength = outgoingBytes.length;
        //clear old data from the ByteBuffer to prevent message corruption
        outbox.clear();
        //put size then message into ByteBuffer
        outbox.putInt(messageLength);
        outbox.put(outgoingBytes);
        //flip buffer for sending
        outbox.flip();
        //transmit message
        try{
            channel.write(outbox);
        } catch(IOException ioe){
            System.out.println("Error sending! Connection disrupted!");
            System.exit(-1);
        }
        System.out.println("Sending: " + message.toString());
    }

    @Override
    public void receive() {
        //clear old data from the ByteBuffer to prevent message corruption
        inbox.clear();
        //receive incoming transmission into ByteBuffer
        try{
            System.out.println("Reading...");
            System.out.println("Bytes Read: " + channel.read(inbox));
        } catch(IOException ioe){
            System.out.println("Error receiving! Connection disrupted!");
            System.exit(-1);
        }
        //flip buffer for reading
        inbox.flip();
        //create appropriately-sized byte[] using the first 4 bytes of the transmission 
        incomingBytes = new byte[inbox.getInt()];
        //transmit from buffer to byte[]
        inbox.get(incomingBytes);
        //read the message
        System.out.println("Received: " + new String(incomingBytes));
    }

    public void kill(){
        try{
            channel.close();
        } catch(IOException ioe){
            System.out.println("Error Closing! Shutting Down!");
            System.exit(-1);
        }
        System.exit(0);
    }

    public int getID(){
        return id;
    }

    public String receiveString(){
        //clear old data from the ByteBuffer to prevent message corruption
        inbox.clear();
        //receive incoming transmission into ByteBuffer
        try{
            System.out.println("Reading...");
            System.out.println("Bytes Read: " + channel.read(inbox));
        } catch(IOException ioe){
            System.out.println("Error receiving! Connection disrupted!");
            System.exit(-1);
        }
        //flip buffer for reading
        inbox.flip();
        //create appropriately-sized byte[] using the first 4 bytes of the transmission 
        incomingBytes = new byte[inbox.getInt()];
        //transmit from buffer to byte[]
        inbox.get(incomingBytes);
        //read the message
        String answer = new String(incomingBytes);
        return answer;
    }
}//end of class