package gov.iti.jets.client.network.service;


import gov.iti.jets.common.network.UserRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class LoginService {
    public void login(String phone, String password, Registry registry){
        try {
            UserRemote obj = (UserRemote) registry.lookup("server");
            System.out.println(obj);
            obj.login(phone,password);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
