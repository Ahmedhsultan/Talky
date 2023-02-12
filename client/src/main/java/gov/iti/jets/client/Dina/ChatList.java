package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.ChatDto;

import java.util.ArrayList;

public class ChatList extends ArrayList<ChatDto>{
    private static ChatList chatList;
    public static ChatList getList(){
        if(chatList == null)
            chatList = new ChatList();

        return chatList;
    }
}
