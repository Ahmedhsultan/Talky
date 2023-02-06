package gov.iti.jets.network;

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
    private RMIManager(){
        try {
            reg = LocateRegistry.createRegistry(1099); //Should be added to environmental variables
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static Registry getRegistry(){
        if(rmiManager == null)
            rmiManager = new RMIManager();

        return rmiManager.reg;
    }
}
