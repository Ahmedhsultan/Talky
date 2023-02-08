package gov.iti.jets.client.network.service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * This is a manager singleton class which handle the access and creation
 * of Registry and get the port number from environmental variables
 * all method is private except static method getRegistry which responsible about creation the Registry
 * and return it to the user
 */
public class RMIManager {
    private static RMIManager rmiManager;
    private Registry reg ;
    private RMIManager() throws RemoteException{
            reg = LocateRegistry.getRegistry(1099);

    }

    public static Registry getRegistry() throws RemoteException{
        if(rmiManager == null)
            rmiManager = new RMIManager();

        return rmiManager.reg;
    }
}
