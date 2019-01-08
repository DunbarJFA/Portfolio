import java.io.Serializable;

public interface ChannelPort{
    public void receive();
    public void broadcast(Serializable message);
    public boolean empty();
}//end of class ChannelPort