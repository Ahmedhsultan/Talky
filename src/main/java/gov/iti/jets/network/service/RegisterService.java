package gov.iti.jets.network.service;

import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.network.UserRemote;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegisterService {
    UserRemote obj;
    public RegisterService(){
        try {
//            String host = InetAddress.getLocalHost().getHostName();
            Registry registry = LocateRegistry.getRegistry("localhost",1099);
            obj = (UserRemote) registry.lookup("UserRemote");
            System.out.println("Client.....");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public  void addUser(UserRegistrationDto user){
        try {
            obj.register(user);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
