package gov.iti.jets.client.presentation.controllers;

import gov.iti.jets.client.Queues.ContactList;
import gov.iti.jets.client.Queues.MyID;
import gov.iti.jets.client.Util.AlertWindow;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.common.util.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Circle userPic;
    @FXML
    private Pane containerPane;
    @FXML
    private Label userName;

    @FXML
    private Label bio;
    @FXML
    private CheckBox chatbot;
    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Label userPhoneNumber;

    @FXML
    private Label nameError;
    @FXML
    private Label bioError;

    FileChooser fileChooser = new FileChooser();
    File file;
    TextField userNameText, bioText;

    ContactDto contact = ContactList.getList().stream().filter(x->x.getId().equals(MyID.getInstance().getMyId())).toList().get(0);

    ObservableList<String> statusObservableList = FXCollections.observableArrayList(Constants.ONLINE_STATUS_AVAILABLE,Constants.ONLINE_STATUS_AWAY,Constants.ONLINE_STATUS_BUSY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        putUserDataOnPane();
        userNameText = new TextField(userName.getText());
        bioText = new TextField(bio.getText());
        try {
            userPic.setFill(new ImagePattern(new Image(saveUserImage(contact),200,200,false,true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        statusComboBox.setItems(statusObservableList);
        statusComboBox.setValue(contact.getIsOnlineStatus());
        System.out.println(contact.getIsOnlineStatus());

    }


    private  void putUserDataOnPane () {
        ContactDto contact = ContactList.getList().stream().filter(x->x.getId().equals(MyID.getInstance().getMyId())).toList().get(0);

        putBioOnPane(contact.getBio());
        putChatbotOnPane(contact.isBotMode());
//        putImageOnPane(userSessionDto.getUser().getImgPath(), userSessionDto.getUser().getImage());
        putUserNameOnPane(contact.getName());
        putPhoneNumOnPane(contact.getId());
        putIsOnlineStatusOnPane(contact.getIsOnlineStatus());
        //putImageOnPane();
    }
    public void changeBio(ActionEvent actionEvent) {
        bioText.setLayoutX(38);
        bioText.setLayoutY(287);
        bioText.setPrefHeight(30);
        bioText.setPrefWidth(200);
        containerPane.getChildren().set(1,bioText);
    }

    public void saveChanges(ActionEvent actionEvent) {
        ContactDto contact = ContactList.getList().stream().filter(x->x.getId().equals(MyID.getInstance().getMyId())).toList().get(0);

        if(Validation.validateBio(bioText, bioError ) & Validation.validateName(userNameText.getText(),userNameText, nameError)) {
           contact.setBio(bioText.getText());
            contact.setName(userNameText.getText());
            contact.setBotMode(chatbot.isSelected());
            contact.setIsOnlineStatus(statusComboBox.getSelectionModel().getSelectedItem());
            contact.setId(userPhoneNumber.getText());

            if(file!=null) {
                contact.setImgPath(userPhoneNumber.getText() + file.getPath().substring(file.getPath().indexOf(".")));
                try {
                    contact.setImage(Constants.imageToByteArray(file.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            try {
//                UserRemote userRemote = RMIManager.lookUpRegister();
//                userRemote.updateProfile(userSessionDto.getUser());
//            } catch (RemoteException | NotBoundException e) {
//                e.printStackTrace();
//            }
            System.out.println( contact.getBio());
            try {
                IServer iServer = RMIManager.lookUpIServer();
                UserDto user = new UserDto();
                user.setId(contact.getId());
                user.setName(contact.getName());
                user.setIsOnlineStatus(contact.getIsOnlineStatus());
                user.setBio(contact.getBio());
                user.setImage(contact.getImage());
                user.setImgPath(contact.getImgPath());
                user.setBotMode(contact.isBotMode());
                iServer.editUser(user);
                AlertWindow alertWindow = new AlertWindow("Your profile has been updated successfully");

            } catch (RemoteException e) {
                e.printStackTrace();
                AlertWindow alertWindow = new AlertWindow("Failed to update your Profile");
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void changeUserPic(ActionEvent actionEvent) {
        fileChooser.getExtensionFilters().add(  new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            userPic.setFill(new ImagePattern(new Image(file.toURI().toString(),200,200,false,true)));
            System.out.println(file.getPath());
        }
    }

    public void changeName(ActionEvent actionEvent) {
        userNameText.setLayoutX(38);
        userNameText.setLayoutY(226);
        userNameText.setPrefHeight(30);
        userNameText.setPrefWidth(200);
        containerPane.getChildren().set(0,userNameText);
    }



    private void putUserNameOnPane(String userName) {
        ((Label)containerPane.lookup("#userName")).setText(userName);
    }

    private void putPhoneNumOnPane(String phone) {
        ((Label)containerPane.lookup("#userPhoneNumber")).setText(phone);
    }


    private void putIsOnlineStatusOnPane(String status) {
        ObservableList<String> statusList = FXCollections.observableArrayList(Arrays.asList("available", "away", "busy"));
        ((ComboBox<String>)(containerPane.lookup("#userStatus"))).setItems(statusList);
        ((ComboBox<String>)(containerPane.lookup("#userStatus"))).getSelectionModel().select(status);
    }

    private void putBioOnPane(String bio) {
        ((Label)containerPane.lookup("#bio")).setText(bio);
    }

    private void putChatbotOnPane(boolean enabled) {
        ((CheckBox)containerPane.lookup("#chatbot")).setSelected(enabled);
    }

    private  void putImageOnPane() {
        try {
            ((Circle) containerPane.lookup("#userPic")).setFill(new ImagePattern(new Image(saveUserImage(contact),100,100,false,true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String  saveUserImage(UserDto dto) throws IOException {
        String path = Constants.USER_IMAGES_DIR +dto.getImgPath();
        Constants.byteArrayToImage(dto.getImage(), URLDecoder.decode(path, "UTF-8"));
        return URLDecoder.decode(path, "UTF-8");
    }
    public String  saveUserImage(ContactDto dto) throws IOException {
        String path = Constants.USER_IMAGES_DIR +dto.getImgPath();
        Constants.byteArrayToImage(dto.getImage(), URLDecoder.decode(path, "UTF-8"));
        return URLDecoder.decode(path, "UTF-8");
    }

}
