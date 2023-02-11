package gov.iti.jets.client.network.service;


import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.network.UserRemote;
import gov.iti.jets.common.network.server.UserRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class LoginService {
    public UserSessionDto login(String phone, String password, Registry registry){
        UserSessionDto userSessionDto = null;
        try {
            UserRemote obj = RMIManager.lookUpRegister();
            System.out.println(obj);
            userSessionDto = obj.login(phone,password);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
        return userSessionDto;
    }
}
