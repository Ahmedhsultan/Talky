package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.MessageDto;

import java.util.HashMap;

public class NotificationQueue extends HashMap<Integer, MessageDto> {
    private static NotificationQueue notificationQueue;
    public static NotificationQueue getList(){
        if(notificationQueue == null)
            notificationQueue = new NotificationQueue();

        return notificationQueue;
    }
}
