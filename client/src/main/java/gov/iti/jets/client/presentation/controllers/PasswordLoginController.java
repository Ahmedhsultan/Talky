package gov.iti.jets.client.presentation.controllers;


import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.client.network.service.LoginService;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.dto.UserSessionDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;


public class PasswordLoginController implements Initializable {

    @FXML
    private TextField password;

    @FXML
    private Button nextBtn;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Pane passwordPane;

    @FXML
    private Label error;

    public static UserSessionDto userSessionDto;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToChat(ActionEvent actionEvent) {
        System.out.println(PhoneLoginController.phoneNo);
        LoginService log = new LoginService();

        log.login(PhoneLoginController.phoneNo,password.getText());
        System.out.println("Login success");

        SceneManager.getSceneManager().switchToChatScene();
    }

    public void loadRegistration(ActionEvent actionEvent) {
        SceneManager.getSceneManager().switchToRegistrationScene();
    }

    public void goToPhone(ActionEvent actionEvent) {
            PaneManager.getPaneManager().putLoginPhonePane();
    }


}
