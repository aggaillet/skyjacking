package message_controller;

public interface IMessageController {
    Position requestPosition();
    boolean sendSpoofedMsg(byte[] byteMessage);
}
