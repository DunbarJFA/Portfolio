package dist_sem;

import java.io.Serializable;

public class NodeInfo implements Serializable{
    int nodeID;
    int nodePort;
    String nodeIP;

    public NodeInfo(int id, int port, String ip){
        this.nodeID = id;
        this.nodePort = port;
        this.nodeIP = ip;
    }
    
    public int getNodeID(){
        return nodeID;
    }

    public int getNodePort(){
        return nodePort;
    }

    public String getNodeIP(){
        return nodeIP;
    }
    
}