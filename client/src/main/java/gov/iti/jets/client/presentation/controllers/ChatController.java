package gov.iti.jets.client.presentation.controllers;


import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.common.dto.*;
import gov.iti.jets.common.util.ChatsMapList;
import gov.iti.jets.common.util.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
                       MessageDto m =  ChatsMapList.getList().get(Integer.parseInt(((Label)(newValue.lookup("#chatID"))).getText()));
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

}
