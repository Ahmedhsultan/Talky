package gov.iti.jets.client.presentation.controllers;


import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.client.network.service.RegisterService;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.dto.UserSessionDto;
import gov.iti.jets.common.dto.registration.UserRegistrationDto;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.common.util.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private DatePicker birthday;
    @FXML
    private ImageView image;
    @FXML
    private Button register;
    FileChooser fileChooser = new FileChooser();
    File file;
    Validation validate;
    UserRegistrationDto userRegistrationDto;
    public static UserSessionDto userSessionDto;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }
    @FXML
    public void addProfileImage(MouseEvent event) {
        fileChooser.getExtensionFilters().add(  new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            image.setImage(new Image(file.toURI().toString(),200,200,false,true));
        }
    }
    @FXML
    void signup(ActionEvent event) throws IOException {
//        if(validation()){
        UserDto user = new UserDto();
        user.setName(name.getText());
        user.setId(phone.getText());
        String s = file.getPath();
        user.setImgPath(phone.getText()+ s.substring(s.indexOf(".")));
        user.setImage(Constants.imageToByteArray(file.getPath()));
        java.sql.Date date =  new Date(birthday.getValue().getYear(),birthday.getValue().getMonthValue(),birthday.getValue().getDayOfMonth());
        user.setDateOfBirth(date);
        System.out.println("success validate");

        userRegistrationDto = new UserRegistrationDto(user,password.getText());
        try {
            userSessionDto = new RegisterService().addUser(userRegistrationDto);
            SceneManager.getSceneManager().switchToChatScene();
        }catch (Exception e){
            e.getMessage();
        }

        SceneManager.getSceneManager().switchToChatScene();
    }
    @FXML
    public void close() {
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();
    }
}
