package gov.iti.jets.server.presentation.controllers;

import gov.iti.jets.server.service.ServerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    ImageView gift;

    @FXML
    ImageView switchButton;

    boolean flag = false;
    private ServerService service = new ServerService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SwitchOff();
    }


    public void switchonOff(MouseEvent mouseEvent) {
        if(flag) {
            SwitchOff();
            service.stopServer();
            flag = false;
        }
        else {
            SwitchOn();
            service.startServer();
            flag = true;
        }
    }

    private void SwitchOff(){
        gift.setImage(new Image("/image/iranserver-iran.gif"));
        switchButton.setImage(new Image("image/icons8-light-switch.png"));
    }
    private void SwitchOn(){
        gift.setImage(new Image("/image/iranserver-blue-bot.gif"));
        switchButton.setImage(new Image("/image/icons8-light-switch-64.png"));
    }
}
