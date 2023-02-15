package gov.iti.jets.server.presentation.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class trackUser implements Initializable {

    @FXML
    private JFXListView userListView;
    ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneObservableList.add(loadPaneUserCard());
        paneObservableList.add(loadPaneUserCard());
        paneObservableList.add(loadPaneUserCard());
        userListView.setItems(paneObservableList);
    }
    private Pane loadPaneUserCard() {
        Pane pane = null;
        try {
            Parent root = FXMLLoader.load(getClass().getResource(String.format("/view/userCard.fxml")));
            pane = (Pane) root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }


}

