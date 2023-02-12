package gov.iti.jets.client.network.service;


import com.jfoenix.controls.JFXSnackbar;
import gov.iti.jets.client.callBack.Martinily;
import gov.iti.jets.client.presentation.controllers.RegisterController;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.dto.registration.UserRegistrationDto;
import gov.iti.jets.common.network.server.UserRemote;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class RegisterService {
//    UserRemote obj;

//    public RegisterService(){
//        try {
////            String host = InetAddress.getLocalHost().getHostName();
//            Registry registry = RMIManager.getRegistry();
//            obj = (UserRemote) registry.lookup("UserRemote");
//            System.out.println("Client.....");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    public UserSessionDto addUser(UserRegistrationDto user){
        UserSessionDto userSessionDto = null;
        try {
            UserRemote obj = RMIManager.lookUpRegister();
            userSessionDto = obj.register(user, new Martinily());
        } catch (RemoteException e) {
//            throw new RuntimeException(e);
            System.out.println(e.getMessage());
            JFXSnackbar snackbar = new JFXSnackbar(new RegisterController().rootPane);
            final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(new Label(e.getMessage()), Duration.seconds(5.33), null);
            snackbar.enqueue(snackbarEvent);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return userSessionDto;
    }
}
