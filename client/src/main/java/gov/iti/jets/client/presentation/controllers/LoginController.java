package gov.iti.jets.client.presentation.controllers;


import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.client.callBack.Martinily;
import gov.iti.jets.client.network.service.LogoutService;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.network.server.UserRemote;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
