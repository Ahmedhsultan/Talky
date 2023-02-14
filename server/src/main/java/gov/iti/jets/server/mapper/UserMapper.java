package gov.iti.jets.server.mapper;


import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.Interfaces.IUser;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.dto.registration.UserRegistrationDto;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserMapper implements BaseMapper<User, UserDto>{

    @Override
    public UserDto toDTO(User user) {
        UserService service = new UserService();
        UserDto dto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .imgPath(user.getImgPath())
                .gender(user.getGender())
                .country(user.getCountry())
                .dateOfBirth(user.getDateOfBirth())
                .isOnlineStatus(user.getIsOnlineStatus())
                .botMode(user.isBotMode())
                .bio(user.getBio())
                .build();
        try {
            dto.setImage(service.getUserImage(dto));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public ContactDto toContactDTO(IUser user ) {
        String path = Constants.USER_IMAGES_DIR;
        try{
            ContactDto dto = ContactDto.builder()
                    .phoneNumber(user.getId())
                    .name(user.getName())
                    .image(Constants.imageToByteArray(path + user.getImgPath()))
                    .picture(user.getImgPath())
                    .isOnlineStatus(user.getIsOnlineStatus())
                    .bio(user.getBio())
                    .build();
            return dto;
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User entity = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .imgPath(userDto.getImgPath())
                .gender(userDto.getGender())
                .country(userDto.getCountry())
                .dateOfBirth(userDto.getDateOfBirth())
                .isOnlineStatus(userDto.getIsOnlineStatus())
                .botMode(userDto.isBotMode())
                .bio(userDto.getBio())
                .build();
        return entity;
    }

    @Override
    public ArrayList<UserDto> toDTOs(Collection<User> users) {
        return (ArrayList<UserDto>) users.
                stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArrayList<User> toEntities(Collection<UserDto> userDtos) {
        return (ArrayList<User>) userDtos.
                stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public User regDtoToEntity(UserRegistrationDto userRegistrationDto)
    {
        User user = toEntity(userRegistrationDto.getUserDto());
        user.setPassword(userRegistrationDto.getPassword());
        return user;

    }
}
