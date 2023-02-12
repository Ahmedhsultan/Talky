package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.Interfaces.IUser;
import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.server.mapper.UserMapper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class NotifyClient {

    private UserService userService;
    private UserMapper userMapper;
    public NotifyClient(){
        userService = new UserService();
        userMapper = new UserMapper();
    }

    public void updateContactList(IClient iClient, List<? extends IUser> userList) throws RemoteException {
        //Mapping to ContactDto
        List<ContactDto> contactDtoList = new ArrayList<>();
        for (IUser user : userList){
            ContactDto contactDto = userMapper.toContactDTO(user);
            contactDtoList.add(contactDto);
        }

        //Notify User
        iClient.addFriend(contactDtoList);
    }
}
