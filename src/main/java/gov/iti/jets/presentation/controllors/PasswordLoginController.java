package gov.iti.jets.presentation.controllors;

import gov.iti.jets.business.services.PaneManager;
import gov.iti.jets.business.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class PasswordLoginController implements Initializable {

    @FXML
    private TextField phone;

    @FXML
    private Button nextBtn;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Pane passwordPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void goToChat(ActionEvent actionEvent) {
        SceneManager.getSceneManager().switchToChatScene();

    }

    public void loadRegistration(ActionEvent actionEvent) {
        SceneManager.getSceneManager().switchToRegistrationScene();
    }

    public void goToPhone(ActionEvent actionEvent) {
        PaneManager.getPaneManager().putLoginPhonePane();
    }


}