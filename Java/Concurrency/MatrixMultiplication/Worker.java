import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker{
    private int port;
    private String coorIP;
    private int coorPort;
    private int myID;

    ServerSocket servSoc;

    DataInputStream fromCoor;
    DataOutputStream toCoor;
    
    DataInputStream fromRight;
    DataInputStream fromBottom;
    DataOutputStream toLeft;
    DataOutputStream toTop;

    private String leftIP;
    private int leftPort;

    private String upIP;
    private int upPort;

    int[][] a;
    int[][] b;
    int[][] c;


    public static void main(String[] args) {
        if (args.length != 3){
            System.out.println("Program requires worker port number, coordinator IP, and coordinator port number.");
            System.exit(0);
        }

        Worker worker = new Worker(Integer.parseInt(args[0]),args[1],Integer.parseInt(args[2]));
        worker.connectToCoordinator();
        worker.connectToNeighbors();
    }
    public Worker(int portNum, String IP, int cPort){
        port = portNum;
        coorIP = IP;
        coorPort = cPort;
    }

    public void connectToCoordinator(){
        try {
            //make socket
            Socket socket = new Socket(coorIP,coorPort);
            System.out.println("Socket Established");
            //establish connection to coordinator
            toCoor = new DataOutputStream(socket.getOutputStream());
            //pass worker listening port to coordinator
            toCoor.writeInt(port);
            System.out.println("My Port sent!");
            toCoor.writeInt(0);
            fromCoor = new DataInputStream(socket.getInputStream());
            //here is where you would accept info about the incoming matrices
            String message = fromCoor.readUTF();
            System.out.println("Received:" + message);
            myID = fromCoor.readInt();
            System.out.println("My Worker ID = " + myID);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public int getID(){
        return this.myID;
    }

    public void connectToNeighbors(){
        try {
            leftIP = fromCoor.readUTF();
            leftPort = fromCoor.readInt();

            upIP = fromCoor.readUTF();
            upPort = fromCoor.readInt();

            if(myID%2 == 0){
                //even worker
                while (true) {
                    try{
                        Socket socket = new Socket(leftIP,leftPort);
                        toLeft = new DataOutputStream(socket.getOutputStream());
                        Socket sc = servSoc.accept();
                        fromRight = new DataInputStream(sc.getInputStream());
                        break;
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            }else{
                //odd worker
                while(true){
                    try {
                        Socket sc = servSoc.accept();
                        fromRight = new DataInputStream(sc.getInputStream());
                        Socket socket = new Socket(leftIP, leftPort);
                        toLeft = new DataOutputStream(socket.getOutputStream());
                        break;
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }

            if(myID%2 == 0){
                //even worker
                while (true) {
                    try{
                        Socket socket = new Socket(upIP,upPort);
                        toTop = new DataOutputStream(socket.getOutputStream());
                        Socket sc = servSoc.accept();
                        fromBottom = new DataInputStream(sc.getInputStream());
                        break;
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            }else{
                //odd worker
                while(true){
                    try {
                        Socket sc = servSoc.accept();
                        fromBottom = new DataInputStream(sc.getInputStream());
                        Socket socket = new Socket(upIP, upPort);
                        toTop = new DataOutputStream(socket.getOutputStream());
                        break;
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
            System.out.println("Neighbors Established!");
            toLeft.writeInt(myID);
            toTop.writeInt(myID);
            System.out.println("I am " + myID + ", and my neighbors are: ");
            System.out.println(fromRight.readInt() + " to my right");
            System.out.println(fromBottom.readInt() + " below me");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}