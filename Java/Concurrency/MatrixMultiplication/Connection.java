import java.io.*;
import java.net.*;

public class Connection{
    String name;
    int port;
    ServerSocket sock;

    public Connection(int portNum){
        this.port = portNum;
        try {
            sock = new ServerSocket(port);
        } catch (IOException ioe) {
            System.out.println("Unable to listen on port " + port + "," + ioe);
            System.exit(-1);
        }
    }

    //for Coordinator
    public DataIO acceptConnection(){
        DataIO dio = null;
        while(true){
            System.out.println("Establishing Connection...");
            try {
                Socket socket = sock.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                dio = new DataIO(input, output);
                break; 
            } catch (IOException ioe) {
                System.out.println("Cannot connect to worker...");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Try Again.");
                }
            }
        }
        return dio;
    }

    public DataInputStream acceptToRead(){
        DataInputStream input = null;
        while (true){
            try {
                Socket socket = sock.accept();
                input = new DataInputStream(socket.getInputStream());
                break;
            } catch (IOException ioe) {
                System.out.println("Cannot connect to worker...");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Try Again.");
                }
            }
        }
        return input;
    } 

    public DataOutputStream acceptToWrite(){
        DataOutputStream output = null;
        while(true){
            try {
                Socket socket = sock.accept();
                output = new DataOutputStream(socket.getOutputStream());
                break;
            } catch (IOException ioe) {
                System.out.println("Cannot connect to worker..."); 
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Try Again.");
                }   
            }
        }
        return output;
    }

    public DataIO connectIO(String ip, int port){
        DataIO dio = null;
        while(true){
            try {
                Socket socket = sock.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                dio = new DataIO(input, output);
                break;
            } catch (IOException ioe) {
                System.out.println("Cannot connect to IP: " + ip + ", on port:" + port + "...");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Try Again.");
                }
            }
        }
        return dio;
    }
    
    public DataOutputStream connectToWrite(String ip, int port){
        DataOutputStream output = null;
        while (true) {
            try {
                Socket socket = new Socket(ip, port);
                output = new DataOutputStream(socket.getOutputStream());
                break;
            } catch (IOException ioe) {
                System.out.println("Cannot connect to IP: " + ip + ", on port:" + port + "...");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Try Again.");
                }                
            }
        }
        return output;
    }
    
    public DataInputStream connectToRead(String ip, int port){
        DataInputStream input = null;
        while (true) {
            try {
                Socket socket = new Socket(ip,port);
                input = new DataInputStream(socket.getInputStream());
                break;
            } catch (IOException ioe) {
                System.out.println("Cannot connect to IP: " + ip + ", on port:" + port + "...");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("Try Again.");
                }
            }
        }
        return input;
    }
}