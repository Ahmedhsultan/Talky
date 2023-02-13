package gov.iti.jets.server;


import gov.iti.jets.server.controller.IServerController;
import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.network.RMIManager;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class Main {
    public static boolean createDirectory(String serverpath) throws RemoteException {
        File serverpathdir = new File(serverpath);
        return serverpathdir.mkdir();

    }
    public static void main(String[] args) {
//        try {
//            createDirectory("E:\\hi");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        try {
            Registry reg = RMIManager.getRegistry();
            reg.rebind("register", new UserController());
            reg.rebind("iserver", new IServerController());
            while(true)
            {

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
