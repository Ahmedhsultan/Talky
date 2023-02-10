package gov.iti.jets.client.presentation.controllers;


import gov.iti.jets.client.business.services.PaneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChatController implements Initializable {

    @FXML
    private Button chatsButton;

    @FXML
    private Button contactsButton;

    @FXML
    private Button invitationsButton;

    @FXML
    private Button notificationsButton;

    @FXML
    private Label currentPane;
    @FXML
    private ListView leftList;

    @FXML
    private TextField searchField;


    ObservableList<Double> b = FXCollections.observableArrayList();
    ObservableList<Pane> p = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Double i =0.0 ; i<100.0; i+=10.0){
            b.add(i);
        }
        chatsButton.fire();
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
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);
        chatsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        p.clear();
        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putRecentChatCard();
            Circle img = (Circle) temp.getChildren().get(1);
            img.setFill(new ImagePattern(new Image(("/image/user.png"),100,100,false,true)));
            temp.getChildren().set(1, img);
            p.add(temp);


        }

        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putRecentChatCard();
            Label label = (Label) temp.getChildren().get(3);
            label.setText("Ahmed Ayman");
            temp.getChildren().set(3, label);
            p.add(temp);
        }
        leftList.setItems(p);

    }

    public void openContacts(ActionEvent actionEvent) {
        currentPane.setText("Contacts");
        invitationsButton.setStyle(null);
        chatsButton.setStyle( null);
        notificationsButton.setStyle(null);
        contactsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        p.clear();
        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putContactCard();
            Circle img = (Circle) temp.getChildren().get(1);
            img.setFill(new ImagePattern(new Image(("/image/user2.png"),100,100,false,true)));
            temp.getChildren().set(1, img);
            p.add(temp);
        }

        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putContactCard();
            Label label = (Label) temp.getChildren().get(3);
            label.setText("Ahmed Ayman");
            temp.getChildren().set(3, label);
            p.add(temp);
        }
        leftList.setItems(p);
    }

    public void openInvitations(ActionEvent actionEvent) {
        currentPane.setText("Invitations");
        chatsButton.setStyle( null);
        contactsButton.setStyle(null);
        notificationsButton.setStyle(null);
        invitationsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        p.clear();
        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putInvitationCard();
            Circle img = (Circle) temp.getChildren().get(1);
            img.setFill(new ImagePattern(new Image(("/image/user2.png"),100,100,false,true)));
            temp.getChildren().set(1, img);
            p.add(temp);
        }

        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putInvitationCard();
            Label label = (Label) temp.getChildren().get(2);
            label.setText("Ahmed Ayman");
            temp.getChildren().set(2, label);
            p.add(temp);
        }
        leftList.setItems(p);
    }
    public void openAddContact(ActionEvent actionEvent) { //openAddContact
        currentPane.setText("Add Contacts");
        chatsButton.setStyle( null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);
        contactsButton.setStyle( "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        p.clear();
        for (double k: b) {
            Pane temp = PaneManager.getPaneManager().putAddContactCard();
            Circle img = (Circle) temp.getChildren().get(1);
            img.setFill(new ImagePattern(new Image(("/image/user2.png"),100,100,false,true)));
            temp.getChildren().set(1, img);
            p.add(temp);
        }
        leftList.setItems(p);
    }
    public void openNotifications(ActionEvent actionEvent) {
    }

    public void searchOnList(KeyEvent keyEvent) {
        ObservableList<Pane> c = FXCollections.observableArrayList(p.stream().filter(x->((Label)(x.lookup("#userName"))).getText().toLowerCase().contains(searchField.getText().toLowerCase())).collect(Collectors.toList()));
        leftList.setItems(c);
    }
}
