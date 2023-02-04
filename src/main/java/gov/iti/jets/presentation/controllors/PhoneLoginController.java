package gov.iti.jets.presentation.controllors;

import gov.iti.jets.business.services.PaneManager;
import gov.iti.jets.business.services.SceneManager;
import gov.iti.jets.util.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class PhoneLoginController implements Initializable {

    @FXML
    private TextField phone;

    @FXML
    private Button nextBtn;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}


    public void goToPassword(ActionEvent actionEvent) {
        if (!phone.getText().isEmpty()){
//            if (Validation.validatePhoneNumber(phone.getText())) {
//                    if() {
                  PaneManager.getPaneManager().putLoginPasswordPane();
//                    }
//                    else{

//                    }
//            }
//            else{
//
//            }
        }
        else {

        }


    }

    public void loadRegisteration(ActionEvent actionEvent) {
        SceneManager.getSceneManager().switchToRegistrationScene();
    }


}