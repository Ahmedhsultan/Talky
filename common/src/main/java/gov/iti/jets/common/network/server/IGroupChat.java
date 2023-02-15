package gov.iti.jets.common.network.server;

import gov.iti.jets.common.dto.MessageDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGroupChat extends Remote {
    public void createGroup(List<String> members) throws RemoteException;
    public void addMember() throws RemoteException;
    public void deleteMember() throws RemoteException;

}
