import message_controller.IMessageController;

import java.security.InvalidParameterException;
import java.time.OffsetDateTime;

/**
 * @author Angelo G. Gaillet
 */
public class Scheduler {
    private final int resolution_ns;
    private final IMessageController messageController;

    public Scheduler(int resolution_ns, IMessageController messageController){
        this.resolution_ns = resolution_ns;
        this.messageController = messageController;
    }
    public void scheduleSend(byte[] message, OffsetDateTime time){
        Runnable run = new Runnable() {
            public void run() {
                if (OffsetDateTime.now().isAfter(time)){
                    throw new InvalidParameterException("The given time is in the past");
                }
                while (OffsetDateTime.now().isBefore(time)) {
                    try {
                        wait(0, resolution_ns);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                messageController.sendSpoofedMsg(message);
            }
        };
        new Thread(run).start();

    }
}
