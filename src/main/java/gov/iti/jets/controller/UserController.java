package gov.iti.jets.controller;

import gov.iti.jets.entity.User;
import gov.iti.jets.network.apis.UserRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserController extends UnicastRemoteObject implements UserRemote {


    protected UserController() throws RemoteException {
    }

    @Override
    public void register(User user) throws RemoteException {

    }

    @Override
    public void login(String phone, String password) throws RemoteException {

    }
}
