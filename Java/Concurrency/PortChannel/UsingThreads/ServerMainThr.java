public class ServerMainThr{
    public static void main(String[] args) {
        ChannelPortByThr server;
        int portID = Integer.parseInt(args[0]);
        int endpointCount = Integer.parseInt(args[1]);
        //create server
        server = new ChannelPortByThr(portID, endpointCount);
        //prepare to receive connections    
        server.setup();
        //receive messages from endpoints
        server.startListeners();
        //broadcast messages to all endpoints
    }
}