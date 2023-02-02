package gov.iti.jets.presentation.controllors;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.util.Constants;
import gov.iti.jets.util.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

public class RegisterController implements Initializable {

    FileChooser fileChooser = new FileChooser();
    File file;
    private Stage stage;
    private Scene scene;


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

    Validation validate;
    String gender;
    UserDto user = new UserDto();
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
        }
        if(radioMale.isSelected()){
            gender = "Male";
        }else if(radioFemal.isSelected()){
            gender = "Female";
        }
    }
    @FXML
    void signup(ActionEvent event) {
        if(validation()){
            user.setName(firstName.getText() + " " + lastName.getText());
            user.setId(phone.getText());
            user.setEmail(email.getText());
            if(radioMale.isSelected()) { user.setGender("Male");}
            else { user.setGender("Female");}
            user.setBio(bio.getText());
            user.setCountry(country.getSelectionModel().getSelectedItem());
            Date.from(dateOfBirth.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()); // we have to retype zone id
            System.out.println("success validate");
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
            validate.validateGender(toggleGroup,invalidGender))
        {
            System.out.println("valid name");
            val = true;
        }else{val = false;}
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
}
