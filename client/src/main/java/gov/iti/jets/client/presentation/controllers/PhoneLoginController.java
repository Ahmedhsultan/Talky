package gov.iti.jets.client.presentation.controllers;


import gov.iti.jets.client.Main;
import gov.iti.jets.client.Util.AlertWindow;
import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.client.network.service.LoginService;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.util.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;


public class PhoneLoginController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Hyperlink register;
    public static String phoneNo;
    public static UserSessionDto userSessionDto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {    }

    @FXML
    public void goToPassword(ActionEvent actionEvent) {
//        if(Validation.validatePhoneNumber(userName,error)){
        phoneNo = userName.getText();
        LoginService log = new LoginService();

        try {
            userSessionDto = log.login(PhoneLoginController.phoneNo,password.getText());
            SceneManager.getSceneManager().switchToChatScene();
        } catch (RemoteException e) {
            new AlertWindow(e.getMessage());
        }
//        }
    }

    @FXML
    public void loadRegistration(ActionEvent actionEvent) {
        SceneManager.getSceneManager().switchToRegistrationScene();
    }
    @FXML
    public void close() {
        Stage stage = (Stage) login.getScene().getWindow();
        stage.close();
    }
}
