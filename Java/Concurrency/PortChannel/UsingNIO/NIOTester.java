public class NIOTester{
    public static void main(String[] args) {
        int port = 0;
        if(args.length == 1){
            port = Integer.parseInt(args[0]);
        }else{
            System.out.println("Incorrect Argument: Provide port number");
            System.exit(-1);
        }
        ChannelPortByNIO nio = new ChannelPortByNIO(port);
        nio.setup();
    }
}