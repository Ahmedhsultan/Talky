package gov.iti.jets.client.network.service;


import gov.iti.jets.client.Queues.MyID;
import gov.iti.jets.client.Util.Cashing;
import gov.iti.jets.client.callBack.IClientImpl;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.network.server.UserRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginService {
    public UserSessionDto login(String phone, String password) throws RemoteException{
        //Add current user ID
        MyID.getInstance(phone,password);
        //Cash password and id
        Cashing.cash();

        UserSessionDto userSessionDto = null;
        try {
            UserRemote obj = RMIManager.lookUpRegister();
            userSessionDto = obj.login(phone,password, new IClientImpl());
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Login!!");
        } catch (NotBoundException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to Login!!");
        }
        return userSessionDto;
    }
}
