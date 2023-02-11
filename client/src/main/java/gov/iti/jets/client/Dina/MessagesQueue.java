package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.MessageDto;
import java.util.HashMap;
import java.util.List;

public class MessagesQueue extends HashMap<Long, List<MessageDto>> {
    private static MessagesQueue messagesQueue;
    public static MessagesQueue getList(){
        if(messagesQueue == null)
            messagesQueue = new MessagesQueue();

        return messagesQueue;
    }
}
