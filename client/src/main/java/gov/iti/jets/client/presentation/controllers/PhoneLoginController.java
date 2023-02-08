package gov.iti.jets.client.presentation.controllers;


import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.common.util.Validation;
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


public class PhoneLoginController implements Initializable {

    @FXML
    private TextField phone;

    @FXML
    private Button nextBtn;

    @FXML
    private Hyperlink registerLink;
    public static String phoneNo;

    @FXML
    private Pane pane;

    @FXML
    private Label error;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {    }


    public void goToPassword(ActionEvent actionEvent) {
        if(Validation.validatePhoneNumber(phone,error)){
            System.out.println(phone.getText());
            phoneNo = phone.getText();
            PaneManager.getPaneManager().putLoginPasswordPane();
        }
    }

    public void loadRegistration(ActionEvent actionEvent) {
        SceneManager.getSceneManager().switchToRegistrationScene();
    }


}
