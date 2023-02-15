package gov.iti.jets.client.Util;

import gov.iti.jets.client.Dina.*;

public class ClearQueues {
    public static void clearAllQueues(){
        ChatList.getList().clear();
        ContactList.getList().clear();
        InvitationQueue.getList().clear();
        MessagesQueue.getList().clear();
        MyID.getInstance().clear();
        NotificationQueue.getList().clear();
    }
}
