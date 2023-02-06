package gov.iti.jets.network.service;

import com.jfoenix.controls.JFXSnackbar;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.network.UserRemote;
import gov.iti.jets.presentation.controllers.RegisterController;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class LoginService {
    public void login(String phone, String password, Registry registry){
        try {
            UserRemote obj = (UserRemote) registry.lookup("server");
            obj.login(phone,password);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
