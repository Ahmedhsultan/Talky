package gov.iti.jets.client.Util;

import gov.iti.jets.client.Queues.NotificationQueue;
import gov.iti.jets.client.network.service.PullOnlineUsersFromServer;
import gov.iti.jets.common.dto.NotificationDto;
import javafx.scene.layout.AnchorPane;

public class NotificationManager {
    public static void createNotification(NotificationDto notificationDto, AnchorPane notification){
        NotificationIcon.increamentNotification(notification);
        if (notificationDto.getType().equals("message")){
            NotificationHint notificationHint = new NotificationHint(notificationDto.getName(),notificationDto.getMsg(),notificationDto.getBytes());
//            NotificationQueue.getList().add(notificationDto);
        }else if (notificationDto.getType().equals("onlineStatus")){
            NotificationHint notificationHint = new NotificationHint(notificationDto.getName(),notificationDto.getOnlineStatus(),notificationDto.getBytes());
//            NotificationQueue.getList().add(notificationDto);
        }
    }

    public static void clearNotificationIcon(){
        NotificationIcon.clearNotification();
    }
}
