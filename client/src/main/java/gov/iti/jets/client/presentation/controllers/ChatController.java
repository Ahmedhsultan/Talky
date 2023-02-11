package gov.iti.jets.client.presentation.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.callBack.IClientInvitation;
import gov.iti.jets.client.network.service.InvitationService;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.common.util.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
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
    private ImageView addIcon;
    @FXML
    private ListView leftList;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView addContactCard;
    @FXML
    private ImageView deleteContactCard;

    @FXML
    private JFXButton btnAddContacts;

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

        addIcon.setImage(new Image("image/icons-add.png"));
        addIcon.setDisable(false);
        addContactCard.setImage(null);
        addContactCard.setDisable(true);
        deleteContactCard.setImage(null);
        deleteContactCard.setDisable(true);
        btnAddContacts.setVisible(false);


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

        addIcon.setImage(new Image("image/icons-add.png"));
        addIcon.setDisable(false);
        addContactCard.setImage(null);
        addContactCard.setDisable(true);
        deleteContactCard.setImage(null);
        deleteContactCard.setDisable(true);
        btnAddContacts.setVisible(false);

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
        addIcon.setImage(null);
        addIcon.setDisable(true);
        addContactCard.setImage(null);
        addContactCard.setDisable(true);
        deleteContactCard.setImage(null);
        deleteContactCard.setDisable(true);
        btnAddContacts.setVisible(false);

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
    public void openNotifications(ActionEvent actionEvent) {
    }

    public void searchOnList(KeyEvent keyEvent) {
        ObservableList<Pane> c = FXCollections.observableArrayList(p.stream().filter(x->((Label)(x.lookup("#userName"))).getText().toLowerCase().contains(searchField.getText().toLowerCase())).collect(Collectors.toList()));
        leftList.setItems(c);
    }

    @FXML
    void AddNewContacts(MouseEvent event) {
        Pane temp = PaneManager.getPaneManager().putAddContactCard();
        currentPane.setText("Add Contacts");
        currentPane.setStyle("-fx-font-size: 40; ");
        chatsButton.setStyle( null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);

        addIcon.setImage(null);
        addIcon.setDisable(true);
        addContactCard.setImage(new Image("image\\icons-add.png"));
        addContactCard.setDisable(false);
        deleteContactCard.setImage(new Image("image\\removeContact.png"));
        deleteContactCard.setDisable(false);
        btnAddContacts.setVisible(true);

        contactsButton.setStyle( "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        p.clear();
        p.add(temp);
        leftList.setItems(p);
    }






    @FXML
    void deleteContact(MouseEvent event) {
        //System.out.println("Remove "+p.size());
        if(p.size()>1){
            p.remove(p.size()-1);
            leftList.setItems(p);
        }

    }
    @FXML
    void addNewContact(MouseEvent event) {
        Pane temp = PaneManager.getPaneManager().putAddContactCard();
        p.add(temp);
        leftList.setItems(p);
    }
    @FXML
    void addContacts(ActionEvent event) {
        leftList.cellFactoryProperty();
        for (Pane k: p) {
            TextField tx = (TextField) k.getChildren().get(1);
            Label label = (Label) k.getChildren().get(2);
            if(Validation.validatePhoneNumber(tx,label)){
                System.out.println(tx.getText());
                Registry reg = null;
                try {
                    reg = RMIManager.getRegistry();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                IClientInvitation clientInvitation = null;
                try {
                    clientInvitation = new IClientInvitation();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                new InvitationService().sendInvit("01090780888",clientInvitation,tx.getText(),reg);
            }
        }
    }

}
