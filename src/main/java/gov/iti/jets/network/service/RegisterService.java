package gov.iti.jets.network.service;

import com.jfoenix.controls.JFXSnackbar;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.network.UserRemote;
import gov.iti.jets.presentation.controllors.RegisterController;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

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
//            throw new RuntimeException(e);
            System.out.println(e.getMessage());
            JFXSnackbar snackbar = new JFXSnackbar(new RegisterController().rootPane);
            final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(new Label(e.getMessage()), Duration.seconds(5.33), null);
            snackbar.enqueue(snackbarEvent);
        }
    }
}
