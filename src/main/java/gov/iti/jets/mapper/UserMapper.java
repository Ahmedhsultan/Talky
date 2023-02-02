package gov.iti.jets.mapper;

import gov.iti.jets.dto.ContactDto;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserMapper implements BaseMapper<User, UserDto>{

    @Override
    public UserDto toDTO(User user) {
        UserDto dto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .gender(user.getGender())
                .country(user.getCountry())
                .dateOfBirth(user.getDateOfBirth())
                .isOnlineStatus(user.getIsOnlineStatus())
                .botMode(user.isBotMode())
                .bio(user.getBio())
                .build();
        return dto;
    }

    public ContactDto toContactDTO(User user) {
        ContactDto dto = ContactDto.builder()
                .phoneNumber(user.getId())
                .name(user.getName())
                .picture(user.getPicture())
                .isOnlineStatus(user.getIsOnlineStatus())
                .bio(user.getBio())
                .build();
        return dto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User entity = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .picture(userDto.getPicture())
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
}
