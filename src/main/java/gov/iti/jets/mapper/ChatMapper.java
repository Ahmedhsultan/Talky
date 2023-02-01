package gov.iti.jets.mapper;

import gov.iti.jets.dto.ChatDto;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.entity.Chat;
import gov.iti.jets.entity.User;

import java.util.ArrayList;
import java.util.Collection;

public class ChatMapper implements BaseMapper<Chat, ChatDto>{

    @Override
    public ChatDto toDTO(Chat chat) {
        ChatDto dto = ChatDto.builder()
                .id(chat.getId())
                .name(chat.getName())
                .picture_icon(chat.getPicture_icon())
                .build();
        return dto;
    }

    @Override
    public Chat toEntity(ChatDto chatDto) {
        return null;
    }

    @Override
    public ArrayList<ChatDto> toDTOs(Collection<Chat> e) {
        return null;
    }

    @Override
    public ArrayList<Chat> toEntities(Collection<ChatDto> chatDtos) {
        return null;
    }
}
