package gov.iti.jets;


import gov.iti.jets.controller.UserController;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.network.UserRemote;
import gov.iti.jets.util.Validation;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {

//        System.out.println(Validation.validateName("aa"));
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server",new UserController());

//
//            UserController userController = new UserController();
//            UserDto dto = new UserDto();
//            dto.setPhoneNumber("01111315011");
//
//            userController.register(new UserRegistrationDto(dto,"asasasasaasa"));
        } catch (RemoteException e) {
//            e.printStackTrace();
        }

        System.out.println("Hello World!");
    }


}
