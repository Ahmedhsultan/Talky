package gov.iti.jets.client.callBack;


import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.common.util.ChatsMapList;
import gov.iti.jets.common.util.ConnectionFlag;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CheckConnection extends UnicastRemoteObject implements IClient {
    public CheckConnection() throws RemoteException {
    }

    @Override
    public void receive() throws RemoteException {
        ConnectionFlag.getInstance().connectedFlag = true;
    }

    @Override
    public void receiveMessage(int chatId, String senderId, String message) throws RemoteException {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message);
        messageDto.setSenderId(senderId);

        ChatsMapList.getList().put(chatId,messageDto);
    }

    /*
    ChatsMapList.getlist().get(01066366741);
     */
}
