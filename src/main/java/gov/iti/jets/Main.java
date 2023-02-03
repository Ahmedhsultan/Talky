package gov.iti.jets;


import gov.iti.jets.controller.UserController;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.service.UserService;
import gov.iti.jets.util.Constants;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) {

        UserDto userDto = new UserDto();
        userDto.setId("01111315032");
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUserDto(userDto);
        dto.setPassword("abdoamr123");
        String path = Main.class.getClassLoader().getResource("images/users/enter.jpg").getPath();

        try {
            byte[] data = Constants.imageToByteArray(path);
            dto.getUserDto().setImgPath(dto.getUserDto().getId()+".jpg");
            dto.getUserDto().setImage(data);
//            UserService s = new UserService();
            UserController userController = new UserController();
            System.out.println(userController.login(dto.getUserDto().getId(), dto.getPassword()));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
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
