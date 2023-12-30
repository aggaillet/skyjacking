package message_controller;

import gpsutils.GpsPosition;

/**
 * Interface to be implemented by all classes allowing for communication with the UAV (e.g. a simulator or an SDR system).
 * @author A.G. Gaillet
 */
public interface IMessageController {
    /**
     * Get the position of the UAV.
     * @return the true position of the UAV.
     */
    GpsPosition requestPosition();

    /**
     * Transmit the given message.
     * @param byteMessage the bytecode of the message.
     * @return True if the message was sent correctly, false otherwise.
     */
    boolean sendSpoofedMsg(byte[] byteMessage);
}
