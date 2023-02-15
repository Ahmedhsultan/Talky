package gov.iti.jets.server.presentation.controllers;

import gov.iti.jets.common.network.client.IClient;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.server.Util.Queues.StatsLists;
import gov.iti.jets.server.network.RMIManager;
import gov.iti.jets.server.service.ServerService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class AnnouncementController implements Initializable {

    @FXML
    private Button sendBtn;

    @FXML
    private TextArea text;

    private ServerService serverService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            sendBtn.setOnAction(ev->{
                serverService = new ServerService();
                    try {
                        IServer server=  (IServer) RMIManager.getRegistry().lookup("iserver");
                        server.sendAnnouncement(text.getText());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
//                    AnnouncementList.getInstance().getList().add(text.getText());

            });
    }
}
