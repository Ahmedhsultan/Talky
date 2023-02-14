package gov.iti.jets.client.network.service;

import gov.iti.jets.client.Dina.ChatList;
import gov.iti.jets.common.dto.ChatDto;
import gov.iti.jets.common.util.Constants;

import java.util.List;

public class GetChatIdbyUserId {
    public synchronized static Long get(String userId){
        Long chatId = null;
        List<ChatDto> oneToOneChats = ChatList.getList().stream().filter(x ->x.getType() == Constants.CHAT_ONE_TO_ONE).toList();
        for (ChatDto ele : oneToOneChats){
            boolean existed = ele.getMembersIds().stream().anyMatch(y -> y == userId);
            if (existed == true)
                return ele.getId();
        }
        return null;
    }
}
