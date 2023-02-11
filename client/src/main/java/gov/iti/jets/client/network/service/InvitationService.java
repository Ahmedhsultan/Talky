package gov.iti.jets.client.network.service;


import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.client.callBack.IClientInvitation;
import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.network.client.ClientInvitation;
import gov.iti.jets.common.network.server.ServerInvitation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class InvitationService {
    public void sendInvit(String senderID, String receiverID, Registry registry){
        try {
            ServerInvitation obj = (ServerInvitation) registry.lookup("invitation") ;
            System.out.println(obj);
            obj.sendInvitation("01090780888","01152349241");
//            SceneManager.getSceneManager().switchToChatScene();
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}