package gov.iti.jets;


import gov.iti.jets.controller.UserController;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;

import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) {

        UserDto userDto = new UserDto();
        userDto.setPhoneNumber("01111315022");
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUserDto(userDto);
        dto.setPassword("abdoamr123");
        try {
            UserController userController = new UserController();
            System.out.println(userController.register(dto));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//        try {
//            Registry reg = LocateRegistry.createRegistry(1099);
//            reg.rebind("server",new UserController());
//
////
////            UserController userController = new UserController();
////            UserDto dto = new UserDto();
////            dto.setPhoneNumber("01111315011");
////
////            userController.register(new UserRegistrationDto(dto,"asasasasaasa"));
//        } catch (RemoteException e) {
////            e.printStackTrace();
//        }

        System.out.println("Hello World!");
    }


}
