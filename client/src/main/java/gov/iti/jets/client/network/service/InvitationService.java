package gov.iti.jets.client.network.service;


import gov.iti.jets.common.network.server.ServerInvitation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class InvitationService {
    public void sendInvit(String senderID, String receiverID){
        try {
            ServerInvitation obj = RMIManager.lookUpInvitation();
            obj.sendInvitation(senderID,receiverID);
//            SceneManager.getSceneManager().switchToChatScene();
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptInvit(long id,Registry registry){
        try {
            ServerInvitation obj = (ServerInvitation) registry.lookup("invitation") ;
            System.out.println(obj);
            obj.acceptInvitation(id);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void rejectInvit(long id,Registry registry){
        try {
            ServerInvitation obj = (ServerInvitation) registry.lookup("invitation") ;
            System.out.println(obj);
            obj.rejectInvitation(id);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}