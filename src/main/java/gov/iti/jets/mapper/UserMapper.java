package gov.iti.jets.mapper;

import gov.iti.jets.dto.UserDto;
import gov.iti.jets.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserMapper implements BaseMapper<User, UserDto>{

    @Override
    public UserDto toDTO(User user) {
        UserDto dto = UserDto.builder()
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .gender(user.getGender())
                .country(user.getCountry())
                .dateOfBirth(user.getDateOfBirth())
                .isOnlineStatus(user.getIsOnlineStatus())
                .botMode(user.isBotMode())
                .build();
        return dto;
}

    @Override
    public User toEntity(UserDto userDto) {
        User entity = User.builder()
                .phoneNumber(userDto.getPhoneNumber())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .picture(userDto.getPicture())
                .gender(userDto.getGender())
                .country(userDto.getCountry())
                .dateOfBirth(userDto.getDateOfBirth())
                .isOnlineStatus(userDto.getIsOnlineStatus())
                .botMode(userDto.isBotMode())
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
