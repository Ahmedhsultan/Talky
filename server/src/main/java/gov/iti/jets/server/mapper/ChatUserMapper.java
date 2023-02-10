package gov.iti.jets.server.mapper;

import gov.iti.jets.common.dto.ChatUserDto;
import gov.iti.jets.server.entity.ChatUser;
import gov.iti.jets.server.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ChatUserMapper implements BaseMapper<ChatUser, ChatUserDto>{


    @Override
    public ChatUserDto toDTO(ChatUser entity) {
        ChatUserDto dto = ChatUserDto.builder().id(entity.getId())
                .userId(entity.getUser_id()).
                build();
        return  dto;
    }

    @Override
    public ChatUser toEntity(ChatUserDto dto) {
        ChatUser entity = ChatUser.builder().id(dto.getId())
                .user_id(dto.getUserId()).
                build();
        return  entity;
    }

    @Override
    public ArrayList<ChatUserDto> toDTOs(Collection<ChatUser> e) {
        return null;
    }

    @Override
    public ArrayList<ChatUser> toEntities(Collection<ChatUserDto> chatUserDtos) {
        return (ArrayList<ChatUser>)chatUserDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}