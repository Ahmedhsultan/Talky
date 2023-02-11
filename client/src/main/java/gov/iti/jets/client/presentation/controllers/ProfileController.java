package gov.iti.jets.client.presentation.controllers;

import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.network.UserRemote;
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
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

    UserSessionDto userSessionDto;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(PasswordLoginController.userSessionDto != null)
            userSessionDto = PasswordLoginController.userSessionDto;
        else
            userSessionDto = RegisterController.userSessionDto;

        putBioOnPane(userSessionDto.getUser().getBio());
//        putChatbotOnPane(userSessionDto.getUser().get);
//        putImageOnPane(userSessionDto.getUser().getImgPath(), userSessionDto.getUser().getImage());
        putUserNameOnPane(userSessionDto.getUser().getName());
        putPhoneNumOnPane(userSessionDto.getUser().getId());
        putIsOnlineStatusOnPane(userSessionDto.getUser().getIsOnlineStatus());

        userNameText = new TextField(userName.getText());
        bioText = new TextField(bio.getText());
    }

    public void changeBio(ActionEvent actionEvent) {
        bioText.setLayoutX(38);
        bioText.setLayoutY(287);
        bioText.setPrefHeight(30);
        bioText.setPrefWidth(200);
        containerPane.getChildren().set(1,bioText);
    }

    public void saveChanges(ActionEvent actionEvent) {
        if(Validation.validateBio(bioText, bioError ) & Validation.validateName(userNameText.getText(),userNameText, nameError)) {
            userSessionDto.getUser().setBio(bioText.getText());
            userSessionDto.getUser().setName(userNameText.getText());
            userSessionDto.getUser().setBotMode(chatbot.isSelected());
            userSessionDto.getUser().setIsOnlineStatus(statusComboBox.getSelectionModel().getSelectedItem());
            userSessionDto.getUser().setId(userPhoneNumber.getText());
//            String s = file.getPath();
//            userSessionDto.getUser().setImgPath(userPhoneNumber.getText()+ s.substring(s.indexOf(".")));
//            try {
//                userSessionDto.getUser().setImage(Constants.imageToByteArray(file.getPath()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {
                Registry reg = RMIManager.getRegistry();
                UserRemote userRemote = (UserRemote) reg.lookup("server");
                userRemote.updateProfile(userSessionDto.getUser());
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
            System.out.println( userSessionDto.getUser().getBio());
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

    private  void putImageOnPane(String img, byte [] arr) {
        try {
            ((Circle) containerPane.lookup("#userPic")).setFill(new ImagePattern(new Image(Constants.byteArrayToImage(arr, img).getPath(),100,100,false,true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
