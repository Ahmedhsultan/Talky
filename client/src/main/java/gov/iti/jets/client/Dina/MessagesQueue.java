package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.MessageDto;
import java.util.HashMap;

public class MessagesQueue extends HashMap<Long, MessageDto> {
    private static MessagesQueue messagesQueue;
    public static MessagesQueue getList(){
        if(messagesQueue == null)
            messagesQueue = new MessagesQueue();

        return messagesQueue;
    }
}
