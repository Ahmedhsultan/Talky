package gov.iti.jets.util;

import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {


    public static boolean validatePhoneNumber(TextField textField, Label lable)
    {
        boolean isValid = true;
        Pattern pattern = Pattern.compile(Constants.PHONE_PATTERN);
        Matcher matcher = pattern.matcher(textField.getText());
        boolean matchFound = matcher.find();

        if (textField.getText().isEmpty()) {
            setErrorMsg(textField,lable, Constants.FIELD_EMPTY);
            isValid = false;
        }else if(!matchFound){
            setErrorMsg(textField,lable,"Invalid phone");
            isValid = false;
        }else {
            textField.setStyle(Constants.CORRECT_INPUT);
            lable.setText("");
        }
        return isValid;
    }

    public static boolean validatePassword(TextField textField, Label lable)
    {
        boolean isValid = true;
        Pattern pattern = Pattern.compile(Constants.PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(textField.getText());
        boolean matchFound = matcher.find();
        if (textField.getText().isEmpty()) {
            setErrorMsg(textField,lable, Constants.FIELD_EMPTY);
            isValid = false;
        }else if(!matchFound){
            setErrorMsg(textField,lable,"Weak Password");
            isValid = false;
        }else {
            textField.setStyle(Constants.CORRECT_INPUT);
            lable.setText("");
        }
        return isValid;
    }
    public static boolean validateConPassword(TextField textField, Label lable, String password)
    {
        boolean isValid = true;
        if (textField.getText().isEmpty()) {
            setErrorMsg(textField,lable, Constants.FIELD_EMPTY);
            isValid = false;
        }else if(!textField.getText().equals(password)) {
            isValid = false;
            setErrorMsg(textField,lable, "Not matching the password field");
                isValid = false;
            }else {
                textField.setStyle(Constants.CORRECT_INPUT);
                lable.setText("");
            }

        return isValid;
    }
    public static boolean validateName(String name,TextField textField, Label lable)
    {
        boolean isValid = true;
        Pattern pattern = Pattern.compile(Constants.NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        boolean matchFound = matcher.find();
        //Validate First Name
        if (textField.getText().isEmpty()) {
            setErrorMsg(textField,lable, Constants.FIELD_EMPTY);
            isValid = false;
        }else if(!matchFound){
            setErrorMsg(textField,lable,"Name at least 3 characters");
            isValid = false;
        }else {
            textField.setStyle(Constants.CORRECT_INPUT);
            lable.setText("");
        }
        return isValid;
    }

    public static boolean validateEmail(String email,TextField textField, Label lable)
    {
        boolean isValid = true;
        Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.find();
        if (textField.getText().isEmpty()) {
            setErrorMsg(textField,lable, Constants.FIELD_EMPTY);
            isValid = false;
        }else if(!matchFound){
            setErrorMsg(textField,lable,"Invalid email");
            isValid = false;
        }else {
            textField.setStyle(Constants.CORRECT_INPUT);
            lable.setText("");
        }
        if(matchFound) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean validateBio(TextField textField, Label lable)
    {
        boolean isValid = true;
        if (textField.getText().isEmpty()) {
            setErrorMsg(textField,lable, Constants.FIELD_EMPTY);
            isValid = false;
        }else {
            textField.setStyle(Constants.CORRECT_INPUT);
            lable.setText("");
        }
        return isValid;
    }
    public static boolean validateCountry(ComboBox<String> combo, Label lable)
    {
        boolean isValid = true;
        if (combo.getSelectionModel().getSelectedItem() == null) {
            setErrorMsg(combo,lable,"Country is required");
            isValid = true;
        }else {
            combo.setStyle(Constants.CORRECT_INPUT);
            lable.setText("");
        }
        return isValid;
    }
    public static boolean validateDate(DatePicker dateOfBirth, Label lable)
    {
        boolean isValid = true;
        if (dateOfBirth.getValue() == null) {
            setErrorMsg(dateOfBirth,lable,"Date is required");
            isValid = false;
        }else {
            dateOfBirth.setStyle(Constants.CORRECT_INPUT);
            lable.setText("");
        }
        return isValid;
    }
    public static boolean validateGender(ToggleGroup tg, Label lable)
    {
        boolean isValid = true;

        if (tg.getSelectedToggle() == null) {
            lable.setText("Gender is required");
            lable.setStyle(Constants.RED_FONT);
            isValid = false;
        }else{
            lable.setText("");
        }
        return isValid;
    }
    public static boolean validateImage(File f, Label lable)
    {
        boolean isValid = true;

        if (f == null) {
            lable.setText("Please Choose Image");
            lable.setStyle(Constants.RED_FONT);
            isValid = false;
        }else{
            lable.setText("");
        }
        return isValid;
    }
    private static void setErrorMsg(Node tf, Label b, String msg){
        //tf.setStyle(Constants.ERROR_BORDER_RED);
        b.setText(msg);
        b.setStyle(Constants.RED_FONT);
    }

}
