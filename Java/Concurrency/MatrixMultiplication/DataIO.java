import java.io.DataInputStream;
import java.io.DataOutputStream;

public class DataIO{
    DataInputStream input;
    DataOutputStream output;
    
    public DataIO(DataInputStream dis, DataOutputStream dos){
        input = dis;
        output = dos;
    }

    public DataInputStream getInput(){
        return input;
    }

    public DataOutputStream getOutput(){
        return output;
    }    

    public void setInput(DataInputStream dis){
        input = dis;
    }

    public void setOutput(DataOutputStream dos){
        output = dos;
    }
}