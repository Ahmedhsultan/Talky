package gov.iti.jets.client.callBack;

import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.network.client.ClientInvitation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IClientInvitation  extends UnicastRemoteObject implements ClientInvitation {
    public IClientInvitation() throws RemoteException {
    }

    @Override
    public void receiveInvitation(InvitationDto invitationDto) throws RemoteException {

    }
}