package gov.iti.jets.common.network.server;

import java.rmi.Remote;

public interface IServer extends Remote {

    public void sendMessage(long chatId, String senderId, String message);
}
