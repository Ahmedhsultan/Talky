package gov.iti.jets.client.Util;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;

public class NotificationHint {
    public NotificationHint(String title, String msg,byte[] bytes){
        // Create a new stage for the notification window
        Stage notificationHint = new Stage();
        notificationHint.initStyle(StageStyle.TRANSPARENT);
        notificationHint.setAlwaysOnTop(true);


        // Create a label for the notification message
        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label messageLabel = new Label(msg);
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        VBox vBox = new VBox();
        vBox.getChildren().add(titleLabel);
        vBox.getChildren().add(messageLabel);
        // Add the label to a layout pane
        HBox layout = new HBox(10, vBox);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(4,99,244), new CornerRadii(10), Insets.EMPTY)));
        layout.setPadding(new Insets(20));

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);

        layout.getChildren().add(imageView);


        // Get the dimensions of the primary screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        // Set the position of the window to the bottom right corner
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        double windowWidth = notificationHint.getWidth();
        double windowHeight = notificationHint.getHeight();
        double windowX = screenWidth - windowWidth;
        double windowY = screenHeight - windowHeight;

        notificationHint.setX(windowX);
        notificationHint.setY(windowY);

        // Add the layout to the notification stage
        Scene scene = new Scene(layout);
        scene.setFill(Color.TRANSPARENT);
        notificationHint.setScene(scene);

        // Show the notification stage
        notificationHint.show();

        // Close the notification stage after 5 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> notificationHint.close());
        delay.play();
    }
}
