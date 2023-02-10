package gov.iti.jets.client.callBack;


import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.network.IClient;
import gov.iti.jets.client.Dina.MessagesQueue;
import gov.iti.jets.client.Util.ConnectionFlag;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Martinily extends UnicastRemoteObject implements IClient {
    public Martinily() throws RemoteException {
    }

    @Override
    public void receive() throws RemoteException {
        ConnectionFlag.getInstance().connectedFlag = true;
    }

    @Override
    public void receiveMessage(long chatId, String senderId, String message) throws RemoteException {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message);
        messageDto.setSenderId(senderId);

        MessagesQueue.getList().put(chatId,messageDto);
    }
}