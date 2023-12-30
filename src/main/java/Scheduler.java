import message_controller.IMessageController;

import java.security.InvalidParameterException;
import java.time.OffsetDateTime;

/**
 * Class responsible with handling the issue of a send command to the given message controller at a given time.
 * @author A.G. Gaillet
 */
public class Scheduler {
    private final int resolution_ns;
    private final IMessageController messageController;

    /**
     * Constructror.
     * @param resolution_ns time delay between auto-runs. Maximum delay in sending the message with regard to the given time.
     * @param messageController the message controller to be used for message dispatching.
     */
    public Scheduler(int resolution_ns, IMessageController messageController){
        this.resolution_ns = resolution_ns;
        this.messageController = messageController;
    }

    /**
     * Schedule the given message to be sent at the given time.
     * @param message bytecode of the message to be sent.
     * @param time time at which one wishes to send the message.
     */
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
