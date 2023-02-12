package gov.iti.jets.server.presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class ServerSliderController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    private void loadPage(String page){
        Parent root =null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view\\"+page+".fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bp.setCenter(root);
    }

    public void statistics(javafx.scene.input.MouseEvent mouseEvent) {
        loadPage("statistics");
    }

    public void sendAnnounce(javafx.scene.input.MouseEvent mouseEvent) {
        loadPage("sendAnnounce");
    }

    public void register(javafx.scene.input.MouseEvent mouseEvent) {
        loadPage("register");
    }

    public void startStopServer(javafx.scene.input.MouseEvent mouseEvent) {
        bp.setCenter(ap);
    }
}
