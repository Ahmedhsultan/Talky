package gov.iti.jets.server;


import gov.iti.jets.common.dto.ChatDto;
import gov.iti.jets.common.dto.ChatUserDto;
import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.entity.Chat;
import gov.iti.jets.server.network.RMIManager;
import gov.iti.jets.server.service.ChatService;
import gov.iti.jets.server.service.ChatUserService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ChatDto dto = new ChatDto();
        ChatUserService chatUserService=new ChatUserService();
        dto.setName("iti");
        List<ChatUserDto> arr = new ArrayList<>();
        arr.add(new ChatUserDto(9,"01111315033"));
        try {
            chatUserService.addChatGroup(arr);
//            System.out.println( new ChatService().addChat(dto));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
//        try {
////             = LocateRegistry.createRegistry(1099);
//            Registry reg = RMIManager.getRegistry();
//            reg.rebind("server", new UserController());
//            while(true)
//            {
//
//            }
//
//        } catch (RemoteException e) {
////            e.printStackTrace();
//        }

        System.out.println("Hello World!");
    }


}
