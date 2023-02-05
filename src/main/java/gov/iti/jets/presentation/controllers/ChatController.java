package gov.iti.jets.presentation.controllers;

import gov.iti.jets.business.services.PaneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private Button chatsButton;

    @FXML
    private Button contactsButton;

    @FXML
    private Button invitationsButton;

    @FXML
    private Button notifactionsButton;

    @FXML
    private Label currentPane;
    @FXML
    private ListView leftList;


    ObservableList<Double> b = FXCollections.observableArrayList();
    ObservableList<Pane> p = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Double i =0.0 ; i<100.0; i+=10.0){
            b.add(i);
        }
        chatsButton.fire();
        leftList.setItems(p);
//
//
        selectChat();

    }

    private void selectChat( ) {
        leftList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pane>() {
            @Override
            public void changed(ObservableValue<? extends Pane> observable, Pane oldValue, Pane newValue) {

            }
        });

    }

    public void openChats(ActionEvent actionEvent) {
        currentPane.setText("Chats");
        contactsButton.setStyle(null);
        chatsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        p.clear();
        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putRecentChatCard();
            Circle img = (Circle) temp.getChildren().get(1);
            img.setFill(new ImagePattern(new Image(("/image/user.png"),100,100,false,true)));
            temp.getChildren().set(1, img);

            p.add(temp);

        }

    }

    public void openContacts(ActionEvent actionEvent) {
        currentPane.setText("Contacts");
        chatsButton.setStyle( null);
        contactsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        p.clear();
        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putContactCard();
            Circle img = (Circle) temp.getChildren().get(1);
//            img.setFill(new ImagePattern(new Image(("/image/user2.png"),100,100,false,true)));
            temp.getChildren().set(1, img);
            p.add(temp);
        }
    }

    public void openInvitations(ActionEvent actionEvent) {
    }

    public void openNotifications(ActionEvent actionEvent) {
    }
}
