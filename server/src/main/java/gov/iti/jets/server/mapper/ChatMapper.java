package gov.iti.jets.server.mapper;


import gov.iti.jets.common.dto.ChatDto;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.entity.Chat;
import gov.iti.jets.server.service.ChatService;
import gov.iti.jets.server.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ChatMapper implements BaseMapper<Chat, ChatDto>{

    @Override
    public ChatDto toDTO(Chat chat) {
        String path = Constants.CHAT_IMAGES_DIR;
        ChatDto  dto = ChatDto.builder()
                .id(chat.getId())
                .name(chat.getName())
                .imgPath(chat.getImgPath())
                .modified_on(chat.getModified_on())
                .type(chat.getType())
                .build();
        try {
            if(chat.getImgPath()!=null)
            {
                dto.setImage(Constants.imageToByteArray(path + chat.getImgPath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
