package gov.iti.jets.client.Util;

import gov.iti.jets.client.presentation.controllers.MainController;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NotificationIcon extends AnchorPane {
    private Label notificationCountLabel;

    private static NotificationIcon notificationIcon;
    private static int notificationCount = 0;

    public static void increamentNotification(AnchorPane notification){
        if(notificationIcon == null){
            notificationIcon = new NotificationIcon(notificationCount);
            notification.getChildren().add(notificationIcon);
        }
        notificationIcon.setNotificationCount(++notificationCount);
    }
    public static void clearNotification(){
        notificationCount = 0;
        if (notificationIcon!= null)
            notificationIcon.setNotificationCount(0);
    }
    private NotificationIcon(int notificationCount) {
//        Circle circle = new Circle(10, Color.RED);
        notificationCountLabel = new Label(Integer.toString(notificationCount));
        notificationCountLabel.setTextFill(Color.RED);
        notificationCountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));



        getChildren().add( notificationCountLabel);
        setPrefSize(30, 30);
    }

    private void setNotificationCount(int notificationCount) {
        notificationCountLabel.setText(Integer.toString(notificationCount));
    }
}
