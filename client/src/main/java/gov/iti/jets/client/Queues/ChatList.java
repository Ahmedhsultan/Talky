package gov.iti.jets.client.Queues;

import gov.iti.jets.common.dto.ChatDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChatList{
    private static ObservableList<ChatDto> chatList;
    public static ObservableList<ChatDto> getList(){
        if(chatList == null)
            chatList = FXCollections.observableArrayList();

        return chatList;
    }
}
