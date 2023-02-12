package gov.iti.jets.client.presentation.controllers;


import com.jfoenix.controls.JFXButton;
import gov.iti.jets.client.Dina.MessagesQueue;
import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.network.service.InvitationService;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.dto.*;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.common.util.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    private Pane containerPane;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane rightAnchorPane;

    @FXML
    private Label chatName;

    @FXML
    private Circle chatIcon;
    @FXML
    private ImageView addIcon;
    @FXML
    private ImageView addContactCard;
    @FXML
    private ImageView deleteContactCard;

    @FXML
    private JFXButton btnAddContacts;
    private UserSessionDto userSessionDto;
    ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PaneManager.setPrimaryPane("containerPane", containerPane);
        if(PasswordLoginController.userSessionDto != null)
            userSessionDto = PasswordLoginController.userSessionDto;
        else
            userSessionDto = RegisterController.userSessionDto;

        chatsButton.fire();
        selectChat();

    }

    public void setUserSessionDto (UserSessionDto userSessionDto) {
        this.userSessionDto = userSessionDto;
    }


    private void selectChat( ) throws NumberFormatException {
        leftList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pane>() {
            @Override
            public void changed(ObservableValue<? extends Pane> observable, Pane oldValue, Pane newValue) {
                if(newValue!=null)
                    if(currentPane.getText().equals("Chats")) {
                        chatName.setText(((Label) (newValue.lookup("#userName"))).getText());
//                        chatIcon.setFill(new ImagePattern(new Image(Constants.byteArrayToImage(arr, img).getPath(),100,100,false,true)));
                       List<MessageDto> m =  MessagesQueue.getList().get(Integer.parseInt(((Label)(newValue.lookup("#chatID"))).getText()));
//                        for(MessageDto message :)
                    }
            }
        });
    }


    private void appendMessage() {

    }

    @FXML
    private void openChats(ActionEvent actionEvent) {
        searchField.setVisible(true);
        currentPane.setText("Chats");
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);
        leftList.setId("");
        deleteAddDelContact();
        chatsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        for (ChatDto chat : userSessionDto.getChatListDto()) {
            Pane temp = PaneManager.getPaneManager().putRecentChatCard();
            ((Label)(temp.lookup("#chatID"))).setText(String.valueOf(chat.getId()));
            ((Label)(temp.lookup("#chatID"))).setVisible(false);
//            putImageOnPane(chat.getPicture_icon(), temp);
//            putStatusOnPane("NA", temp);
            putUserNameOnPane(chat.getName(), temp);
            putMessageOnPane("hi", temp);
            putTimeOnPane(DateTimeFormatter.ofPattern("dd/MM").format(chat.getModified_on().toLocalDate()), temp);
            paneObservableList.add(temp);
        }

        leftList.setItems(paneObservableList);
    }

    @FXML
    private void openContacts(ActionEvent actionEvent) {
        searchField.setVisible(true);
        currentPane.setText("Contacts");
        invitationsButton.setStyle(null);
        chatsButton.setStyle( null);
        notificationsButton.setStyle(null);
        leftList.setId("");
        deleteAddDelContact();
        contactsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        for (ContactDto contact : userSessionDto.getContactListDto()) {
            Pane temp = PaneManager.getPaneManager().putContactCard();
//            putImageOnPane(contact.getPicture(), temp);
            putStatusOnPane(contact.getIsOnlineStatus(), temp);
            putUserNameOnPane(contact.getName(), temp);
            putBioOnPane(contact.getBio(), temp);
            putOnlineStatusOnPane(contact.getIsOnlineStatus(), temp);
            paneObservableList.add(temp);
        }
        leftList.setItems(paneObservableList);
    }

    @FXML
    private void openInvitations(ActionEvent actionEvent) {
        searchField.setVisible(true);
        currentPane.setText("Invitations");
        chatsButton.setStyle( null);
        contactsButton.setStyle(null);
        notificationsButton.setStyle(null);
        leftList.setId("");
        deleteAddDelContact();
        invitationsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        for(InvitationDto invitation: userSessionDto.getInvitationListDto()) {
//            Pane temp = PaneManager.getPaneManager().putInvitationCard();

//            putImageOnPane(invitation., temp);
//            putUserNameOnPane(contact.getName(), temp);
//            paneObservableList.add(temp);
        }
//        leftList.setItems(paneObservableList);
    }

    @FXML
    private void openNotifications(ActionEvent actionEvent) {
        searchField.setVisible(true);
        currentPane.setText("Notifications");
        chatsButton.setStyle( null);
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        deleteAddDelContact();
        leftList.setId("");
        notificationsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        Pane temp = PaneManager.getPaneManager().putNotificationPane();
        paneObservableList.add(temp);
        leftList.setItems(paneObservableList);
    }

    @FXML
    private void OpenProfile(MouseEvent mouseEvent) {
        currentPane.setText("Profile");
        chatsButton.setStyle( null);
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);
        searchField.setVisible(false);
        paneObservableList.clear();
        leftList.setId("listOfProfile");
        Pane temp = PaneManager.getPaneManager().putProfilePane();
//        putBioOnPane(userSessionDto.getUser().getBio(), temp);
//        putUserNameOnPane(userSessionDto.getUser().getName(), temp);
//        putPhoneNumOnPane(userSessionDto.getUser().getId(), temp);
//        putIsOnlineStatusOnPane(userSessionDto.getUser().getIsOnlineStatus(), temp);
//        putImageOnPane(userSessionDto.getUser().getImgPath(), userSessionDto.getUser().getImage(), temp);
        paneObservableList.add(temp);
        leftList.setItems(paneObservableList);


    }

    @FXML
    private void searchOnList(KeyEvent keyEvent) {
        ObservableList<Pane> c = FXCollections.observableArrayList(paneObservableList.stream().filter(x->((Label)(x.lookup("#userName"))).getText().toLowerCase().contains(searchField.getText().toLowerCase())).collect(Collectors.toList()));
        leftList.setItems(c);
    }


    private void putMessageOnPane(String message, Pane temp) {
        ((Label) temp.lookup("#recentMessage")).setText(message);
    }

    private void putTimeOnPane(String time, Pane temp) {
        ((Label)temp.lookup("#timestamp")).setText(time);
    }

    private void putUserNameOnPane(String userName, Pane temp) {
        ((Label)temp.lookup("#userName")).setText(userName);
    }

    private void putPhoneNumOnPane(String phone, Pane temp) {
        ((Label)temp.lookup("#userPhoneNumber")).setText(phone);
    }


    private void putStatusOnPane(String status, Pane temp) {
        if(status.equals("Offline"))
            ((Circle)(temp.lookup("#statusCircle"))).setFill(Color.RED);
        else if(status.equals("Online"))
            ((Circle)(temp.lookup("#statusCircle"))).setFill(Color.GREEN);
        else if(status.equals("NA"))
            ((Circle)(temp.lookup("#statusCircle"))).setRadius(0);
    }


    private void putOnlineStatusOnPane(String status, Pane temp) {
        if(status.equals("Offline"))
            ((Label)temp.lookup("#onlineStatus")).setText("");
        else
            ((Label)temp.lookup("#onlineStatus")).setText(status);
    }

    private void putBioOnPane(String bio, Pane temp) {
        ((Label)temp.lookup("#bio")).setText(bio);
    }

    private  void putImageOnPane(String img, byte [] arr, Pane temp) {
        try {
            ((Circle) temp.lookup("#userPic")).setFill(new ImagePattern(new Image(Constants.byteArrayToImage(arr, img).getPath(),100,100,false,true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        paneObservableList.clear();
        paneObservableList.add(temp);
        leftList.setItems(paneObservableList);
    }
    @FXML
    void deleteContact(MouseEvent event) {
        //System.out.println("Remove "+p.size());
        if(paneObservableList.size()>1){
            paneObservableList.remove(paneObservableList.size()-1);
            leftList.setItems(paneObservableList);
        }

    }
    @FXML
    void addNewContact(MouseEvent event) {
        Pane temp = PaneManager.getPaneManager().putAddContactCard();
        paneObservableList.add(temp);
        leftList.setItems(paneObservableList);
    }
    @FXML
    void addContacts(ActionEvent event) {
        leftList.cellFactoryProperty();
        for (Pane k: paneObservableList) {
            TextField tx = (TextField) k.getChildren().get(1);
            Label label = (Label) k.getChildren().get(2);
            if(Validation.validatePhoneNumber(tx,label)){
                System.out.println(tx.getText());
                Registry reg = null;
                try {
                    reg = RMIManager.getRegistry();
                } catch (RemoteException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }

                System.out.println("sender id = "+userSessionDto.getUser().getId() + "reciever id  = "+ tx.getText());
                new InvitationService().sendInvit(userSessionDto.getUser().getId(),tx.getText(),reg);
            }
        }
    }
    private void deleteAddDelContact(){
        addIcon.setImage(new Image("image/icons-add.png"));
        addIcon.setDisable(false);
        addContactCard.setImage(null);
        addContactCard.setDisable(true);
        deleteContactCard.setImage(null);
        deleteContactCard.setDisable(true);
        btnAddContacts.setVisible(false);
    }

    public void sendMessage(ActionEvent actionEvent) {
    }
}
