package gov.iti.jets.presentation.controllors;

import gov.iti.jets.business.services.PaneManager;
import gov.iti.jets.business.services.SceneManager;
import gov.iti.jets.util.Validation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaneManager paneManager = PaneManager.getPaneManager();
        paneManager.setPrimaryPane("login",  pane);
        paneManager.putLoginPhonePane();

    }


}
