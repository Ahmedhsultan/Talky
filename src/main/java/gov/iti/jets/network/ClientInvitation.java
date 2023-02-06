package gov.iti.jets.network;

import gov.iti.jets.dto.InvitationDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInvitation extends Remote {

    void receiveInvitation(InvitationDto invitationDto) throws RemoteException;
}
