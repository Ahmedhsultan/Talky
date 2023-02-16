package gov.iti.jets.server.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainServerController implements Initializable {
    @FXML
    BorderPane rightPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPage("dashboard");
    }

    public void ondashboard(ActionEvent actionEvent) {
        loadPage("dashboard");
    }

    public void onUsers(ActionEvent actionEvent) {
        loadPage("users");
    }

    public void onAnnouncement(ActionEvent actionEvent) {
        loadPage("sendAnnounce");
    }

    public void onStatistics(ActionEvent actionEvent) {
        loadPage("statistics");
    }

    private void loadPage(String page){
        Parent root =null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/"+page+".fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rightPane.setCenter(root);
    }
}
