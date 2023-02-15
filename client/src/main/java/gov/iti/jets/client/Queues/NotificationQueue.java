package gov.iti.jets.client.Queues;

import gov.iti.jets.common.dto.NotificationDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NotificationQueue {
    private static ObservableList<NotificationDto> notificationQueue;
    public static ObservableList<NotificationDto> getList(){
        if(notificationQueue == null)
            notificationQueue = FXCollections.observableArrayList();

        return notificationQueue;
    }
}
