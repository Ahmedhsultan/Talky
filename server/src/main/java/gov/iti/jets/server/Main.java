package gov.iti.jets.server;


import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.network.RMIManager;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {

        try {
            Registry reg = RMIManager.getRegistry();
            reg.rebind("register", new UserController());
            while(true)
            {

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
