package dist_sem;
import java.io.Serializable;
public interface ChannelEndpoint{
    public void send(Serializable message);
    public void receive();
}//end of class ChannelEndpoint