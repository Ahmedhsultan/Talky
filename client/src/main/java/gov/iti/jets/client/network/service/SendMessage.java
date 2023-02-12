package gov.iti.jets.client.network.service;

import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.network.server.IServer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SendMessage {
    public synchronized static void send(long chatId, MessageDto messageDto) throws RemoteException, NotBoundException {
        IServer iServer = RMIManager.lookUpIServer();
        iServer.sendMessage(chatId, messageDto);
    }
}
