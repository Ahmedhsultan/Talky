package gov.iti.jets.client.presentation.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
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

    FileChooser fileChooser = new FileChooser();
    File file;
    private Pane pane;
    private Stage stage;
    private Scene scene;
    @FXML
    public AnchorPane rootPane;


    @FXML
    private JFXButton SignUp;

    @FXML
    private TextField bio;

    @FXML
    private TextField confirmPassword;

    @FXML
    private ComboBox<String> country;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField password;

    @FXML
    private TextField phone;

    @FXML
    private JFXRadioButton radioFemal;

    @FXML
    private JFXRadioButton radioMale;
    @FXML
    Circle circle;
    @FXML
    private Label invalidBio;

    @FXML
    private Label invalidConPassword;
    @FXML
    private  Label invalidImage;

    @FXML
    private Label invalidFName;

    @FXML
    private Label invalidLName;

    @FXML
    private Label invalidPassword;

    @FXML
    private Label invalidPhone;
    @FXML
    private Label invalidEmail;
    @FXML
    private Label invalidCountry;
    @FXML
    private Label invalidDate;
    @FXML
    private Label invalidGender;
    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private Hyperlink loginLink;

    Validation validate;
    String gender;
    UserRegistrationDto userRegistrationDto;
    Registry reg;
    public static UserSessionDto userSessionDto;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
//        circle.setFill(new ImagePattern(new Image("user.png",200,200,false,true)));
        addCountryChoiceBox();
        circle.setFill(new ImagePattern(new Image("/image/user.png",200,200,false,true)));
    }
    @FXML
    public void addProfileImage(MouseEvent event) {
        fileChooser.getExtensionFilters().add(  new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            circle.setFill(new ImagePattern(new Image(file.toURI().toString(),200,200,false,true)));
            System.out.println(file.getPath());
        }
    }
    @FXML
    void signup(ActionEvent event) throws IOException {
        if(validation()){
            UserDto user = new UserDto();
            user.setName(firstName.getText() + " " + lastName.getText());
            user.setId(phone.getText());
            user.setEmail(email.getText());
            String s = file.getPath();
            user.setImgPath(phone.getText()+ s.substring(s.indexOf("."))); //image extension
            if(radioMale.isSelected()) { user.setGender("Male");}
            else { user.setGender("Female");}
            user.setBio(bio.getText());
            user.setCountry(country.getSelectionModel().getSelectedItem());
            user.setImage(Constants.imageToByteArray(file.getPath()));
//            try {
//                java.sql.Date date =  new Date(dateOfBirth.getValue().);
                java.sql.Date date =  new Date(dateOfBirth.getValue().getYear(),dateOfBirth.getValue().getMonthValue(),dateOfBirth.getValue().getDayOfMonth());


//                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                user.setDateOfBirth(date);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }

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
    }
    private boolean validation(){
        boolean val = true;
        if(validate. validateName(firstName.getText(),firstName,invalidFName) &
            validate.validateName(lastName.getText(),lastName,invalidLName)&
            validate.validateEmail(email.getText(),email,invalidEmail)&
            validate.validatePhoneNumber(phone, invalidPhone)&
            validate.validatePassword(password,invalidPassword)&
            validate.validateConPassword(confirmPassword,invalidConPassword,password.getText())&
            validate.validateBio(bio,invalidBio)&
            validate.validateCountry(country,invalidCountry)&
            validate.validateDate(dateOfBirth,invalidDate)&
            validate.validateGender(toggleGroup,invalidGender)& validate.validateImage(file,invalidImage))
        {
            val = true;
        }else{
            val = false;
        }
        return val;
    }
    private void addCountryChoiceBox(){
        ObservableList<String> cities = FXCollections.observableArrayList();
        String[] locales1 = Locale.getISOCountries();
        for (String countrylist : locales1) {
            Locale obj = new Locale("", countrylist);
            String[] city = { obj.getDisplayCountry() };
            for (int x = 0; x < city.length; x++) {
                cities.add(obj.getDisplayCountry());
            }
        }
        country.setItems(cities);
    }

    public void goToLogin(ActionEvent actionEvent) {
        SceneManager.getSceneManager().switchToLoginScene();
        PaneManager.getPaneManager().putLoginPhonePane();
    }

}
