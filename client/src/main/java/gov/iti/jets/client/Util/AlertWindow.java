package gov.iti.jets.client.Util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertWindow {
    public AlertWindow(String Msg ){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Confirm");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(Msg);

        Button yesButton = new Button("OK");
        yesButton.setStyle("-fx-background-color: rgba(253,68,68,0.62);");
        yesButton.setMinWidth(150);

        yesButton.setOnAction(e -> {
            window.close();
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(yesButton);
        hBox.setAlignment(Pos.CENTER);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, hBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        layout.setStyle("-fx-border-color: #9900ff; -fx-border-width: 0.5;");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
