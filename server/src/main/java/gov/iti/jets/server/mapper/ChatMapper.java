package gov.iti.jets.server.mapper;


import gov.iti.jets.common.dto.ChatDto;
import gov.iti.jets.server.entity.Chat;

import java.util.ArrayList;
import java.util.Collection;

public class ChatMapper implements BaseMapper<Chat, ChatDto>{

    @Override
    public ChatDto toDTO(Chat chat) {
        ChatDto dto = ChatDto.builder()
                .id(chat.getId())
                .name(chat.getName())
                .imgPath(chat.getImgPath())
                .modified_on(chat.getModified_on())
                .type(chat.getType())
                .build();
        return dto;
    }

    @Override
    public Chat toEntity(ChatDto dto) {
        Chat entity = Chat.builder()
                .id(dto.getId())
                .name(dto.getName())
                .imgPath(dto.getImgPath())
                .modified_on(dto.getModified_on())
                .type(dto.getType())
                .build();
        return entity;
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
