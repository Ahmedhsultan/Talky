package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.MessageDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;

public class MessagesQueue  {
    private static ObservableMap<Long, ObservableList<MessageDto>> messages;

    public static Map<Long, MessageDto> change = new HashMap<>();

    public static ObservableMap<Long, ObservableList<MessageDto>>  getList(){
        if(messages == null)
            messages = FXCollections.observableHashMap();

        return messages;
    }

}
