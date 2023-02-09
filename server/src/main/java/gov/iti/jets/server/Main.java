package gov.iti.jets.server;


import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.network.RMIManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {

//        try {
//            Registry reg = RMIManager.getRegistry();
//            UserController obj = new UserController();
//            reg.rebind("UserRemote",obj);
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        }

//        UserDto userDto = new UserDto();
//        userDto.setId("01111315022");
//        UserRegistrationDto dto = new UserRegistrationDto();
//        dto.setUserDto(userDto);
//        dto.setPassword("abdoamr123");
//        try {
//            UserController userController = new UserController();
//            System.out.println(userController.login(dto.getUserDto().getId(), dto.getPassword()));
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        try {
//             = LocateRegistry.createRegistry(1099);
            Registry reg = RMIManager.getRegistry();
            reg.rebind("server", new UserController());
//            while(true)
//            {
//
//            }
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
