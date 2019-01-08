import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ChannelPortByNIO implements ChannelPort{
    Selector server;
    private int port;
    private final int capacity = 1024;
    private ByteBuffer buffer = ByteBuffer.allocate(capacity);
    private int clientCount;
    private int channelsReady;
    Set<SelectionKey> keysGrabbed;
    SelectionKey currentKey;
    private int messageLength;
    byte[] messageContents;
    private String message;
    ArrayList<SocketChannel> channels = new ArrayList<>();

    public ChannelPortByNIO(int port){
        this.port = port;
        channelsReady = 1;
        clientCount = 0;
    }
    public void setup(){
        try{
            //grab a new Selector
            server = Selector.open();
            //grab a new ServerSocketChannel
            ServerSocketChannel channel = ServerSocketChannel.open();
            //blocking must be false for the Selector to handle the channel
            channel.configureBlocking(false);
            //use the prepared ServerSocketChannel to create a ServerSocket for connecting to a target port
            ServerSocket socket = channel.socket();
            InetSocketAddress target = new InetSocketAddress(port);
            socket.bind(target);
            //connect the channel to the selector
            SelectionKey key = channel.register(server, SelectionKey.OP_ACCEPT);
            System.out.println("Listening on port " + port);
        } catch(IOException ioe){
            System.out.println("Failed to Setup");
            ioe.printStackTrace();
            System.exit(-1);
        }
        //receive and service connections
        while(true){
            try {
               server.select();
            } catch (IOException ioe) {
                System.out.println("Error: Selector Error");
                ioe.printStackTrace();
                System.exit(-1);
            }
            //establish the set of keys from ready channels
            keysGrabbed = server.selectedKeys();
            //iterate through the set, servicing accordingly
            Iterator keyIter = keysGrabbed.iterator();
            while (keyIter.hasNext()){
                currentKey = (SelectionKey) keyIter.next();
                //if the event type is connection
                if(currentKey.isAcceptable()){
                    accept();
                }
                //else if the event type is reading
                else if(currentKey.isReadable()){
                    receive();
                }

                keyIter.remove();
                if(clientCount == 0){
                    broadcast("Close");
                    System.out.println("All clients closed. Shutting Down...");
                    try{
                        server.close();
                    }catch(IOException ioe){
                        System.out.println("Error: IOException While Closing Server!");
                    }
                    System.exit(0);
                }
            }
        }
    }

    public void accept(){
        try{   
            ServerSocketChannel newServerChannel = (ServerSocketChannel) currentKey.channel();
            SocketChannel newChannel = newServerChannel.accept(); 
            newChannel.configureBlocking(false);
            SelectionKey newKey = newChannel.register(server, SelectionKey.OP_READ);
            System.out.println("Connected to " + newChannel);
            clientCount++;
        } catch(IOException ioe){
            System.out.println("Error: Failed to accept connection!");
            ioe.printStackTrace();
        }
    }
    
    @Override
    public void receive() {
        try{
            SocketChannel readChannel = (SocketChannel) currentKey.channel();
            channels.add(readChannel);
            buffer.clear();
            while(readChannel.read(buffer) < 4){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    System.out.println("Error: Receiving Thread Interrupted!");
                    ie.printStackTrace();
                    System.exit(-1);
                }
            }
            buffer.flip();
            messageLength = buffer.getInt();
            messageContents = new byte[messageLength];
            buffer.get(messageContents);
            message = new String(messageContents);
            if (message.equals("Close")){
                System.out.println("Client Requesting Closure");
                clientCount--;
            }else{
                System.out.println("Received: " + message);
                // try {
                //     Thread.sleep(1000);
                // } catch (InterruptedException ie) {
                //     System.out.println("Error: Echoing Thread Interrupted!");
                //     ie.printStackTrace();
                //     System.exit(-1);
                // }
                buffer.rewind();
                readChannel.write(buffer);
                buffer.clear();
            }
        } catch(IOException ioe){
            System.out.println("Error: Connection Failed During Receive");
            ioe.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void broadcast(Serializable message) {
        String closure = message.toString();
        byte[] output = closure.getBytes();
        for (SocketChannel channel : channels){
            try {
                ByteBuffer broadcastBuff = ByteBuffer.allocate(capacity);
                broadcastBuff.putInt(output.length);
                broadcastBuff.put(output);
                broadcastBuff.flip();
                broadcastBuff.rewind();
                channel.write(broadcastBuff);
            } catch (IOException ioe) {
                System.out.println("Error: Connection Failed During Broadcast!");
                ioe.printStackTrace();
                System.exit(-1);
            }
        }
    }

    @Override
    public boolean empty() {
        return false;
    }
}//end of class 