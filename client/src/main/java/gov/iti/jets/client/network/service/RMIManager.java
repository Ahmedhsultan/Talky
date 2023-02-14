package gov.iti.jets.client.network.service;

import gov.iti.jets.common.network.server.IConnection;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.common.network.server.ServerInvitation;
import gov.iti.jets.common.network.server.UserRemote;

import java.rmi.NotBoundException;
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
    public Registry reg ;
    private RMIManager() throws RemoteException{
            reg = LocateRegistry.getRegistry(1099);

    }

    public synchronized static Registry getRegistry() throws RemoteException{
        if(rmiManager == null)
            rmiManager = new RMIManager();

        return rmiManager.reg;
    }

    public synchronized static IServer lookUpIServer() throws RemoteException, NotBoundException {
        return (IServer) RMIManager.getRegistry().lookup("iserver");
    }
    public synchronized static IConnection lookUpConnection() throws RemoteException, NotBoundException {
        return (IConnection) RMIManager.getRegistry().lookup("connection");
    }
    public synchronized static UserRemote lookUpRegister() throws RemoteException, NotBoundException {
        return (UserRemote) RMIManager.getRegistry().lookup("register");
    }
    public synchronized static ServerInvitation lookUpInvitation() throws RemoteException, NotBoundException {
        return (ServerInvitation) RMIManager.getRegistry().lookup("invitation");
    }
}
