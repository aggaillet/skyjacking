package message_controller;

import gpsutils.GpsPosition;

public interface IMessageController {
    GpsPosition requestPosition();
    boolean sendSpoofedMsg(byte[] byteMessage);
}
