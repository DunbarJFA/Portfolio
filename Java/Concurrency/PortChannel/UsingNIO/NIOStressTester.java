import java.util.Scanner;

public class NIOStressTester{
    public static void main(String[] args) {    
        if(args.length != 3){
            System.out.println("Program requires integer node ID, target IP address, and the number of the port to listen on.");
            System.exit(-1);
        }
        
        int start = (int)System.currentTimeMillis();
        
        int nodeID = Integer.parseInt(args[0]);
        String ipAddy = args[1];
        int port = Integer.parseInt(args[2]);
        
        ChannelEndpointByNIO endpoint = new ChannelEndpointByNIO(nodeID, ipAddy, port, 1024);

        endpoint.setup();
        
        for(int i = 0; i <= 15; i++){
            endpoint.send("TEST FROM #" + nodeID + ":" + i);
            endpoint.receive();
        }
        
        endpoint.send("Close");
        
        System.out.println((char)27 + "[33mWaiting for Closing Ack...");
        while (true) {
            String response = endpoint.receiveString();
            
            if(response.equals("Close")){
                System.out.println((char)27 + "[32mReceived Closing Ackowledgement! Shutting Down.");
                break;        
            }
        }
        int end = (int)System.currentTimeMillis();
        int duration = end - start;
        System.out.println("Total Duration: " + duration);
        while(true){
            Scanner scanner = new Scanner(System.in);
            String comment = scanner.nextLine();
            if(comment != null){
                break;
            }
        }
        endpoint.kill();
    }
}