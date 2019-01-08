import java.util.Scanner;

public class EndpointNIOTester{
        public static void main(String[] args) {    
        if(args.length != 3){
            System.out.println("Program requires integer node ID, target IP address, and the number of the port to listen on.");
            System.exit(-1);
        }  
        
        int nodeID = Integer.parseInt(args[0]);
        String ipAddy = args[1];
        int port = Integer.parseInt(args[2]);
        String message1 = "Hey there, neighbor!";
        String message2 = "Howdy from number ";
        String message3 = "My friends call me ";
        String message4 = "It's short for 'Number ";
        String message5 = "Anyway, welcome to the neighborhood! Farewell from Number ";

        ChannelEndpointByNIO endpoint = new ChannelEndpointByNIO(nodeID, ipAddy, port, 1024);
    
        endpoint.setup();
        endpoint.send(message1);
        endpoint.receive();

        endpoint.send(message2 + endpoint.getID() + "!");
        endpoint.receive();
        
        endpoint.send(message3 + endpoint.getID() + ".");
        endpoint.receive();
       
        endpoint.send(message4 + endpoint.getID() + "'...");
        endpoint.receive();
        
        endpoint.send(message5 + endpoint.getID() + "!!");
        
        endpoint.send("Close");
        while (true) {
            String response = endpoint.receiveString();
            
            if(response.equals("Close")){
                System.out.println("Received Closing Ackowledgement! Shutting Down.");
                break;        
            }
        }
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