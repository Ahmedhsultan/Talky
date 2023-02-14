package gov.iti.jets.client.Util;

import gov.iti.jets.client.Dina.MyID;
import gov.iti.jets.client.network.service.LoginService;
import gov.iti.jets.common.dto.UserSessionDto;
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

import java.rmi.RemoteException;

public class ReconnectWindow {
    private static ReconnectWindow reconnectWindow;
    private static Stage window;
    private ReconnectWindow(String Msg) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Reconnect");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(Msg);

        Button yesButton = new Button("Reconnect");
        yesButton.setStyle("-fx-background-color: rgba(253,68,68,0.62);");
        yesButton.setMinWidth(150);

        yesButton.setOnAction(e -> {
            LoginService loginService = new LoginService();
            try {
                UserSessionDto sessionDto = loginService.login(MyID.getInstance().getMyId(),MyID.getInstance().getPassword());
                if(sessionDto != null){
                    reconnectWindow = null;
                    window.close();
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
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

    public static ReconnectWindow getInstance(String... msg){
        if (window == null)
            reconnectWindow = new ReconnectWindow(msg[0]);
        return reconnectWindow;
    }
}
