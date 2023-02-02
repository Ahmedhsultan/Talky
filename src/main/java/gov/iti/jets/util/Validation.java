package gov.iti.jets.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {


    public static boolean validatePhoneNumber(String phone)
    {
        boolean isValid = false;
        Pattern pattern = Pattern.compile(Constants.PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        boolean matchFound = matcher.find();
        if(matchFound) {
            isValid = true;
        }
//        System.out.println(isValid);
        return isValid;
    }

    public static boolean validatePassword(String password)
    {
        boolean isValid = false;
        Pattern pattern = Pattern.compile(Constants.PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        boolean matchFound = matcher.find();
        if(matchFound) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean validateName(String name)
    {
        boolean isValid = false;
        Pattern pattern = Pattern.compile(Constants.NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        boolean matchFound = matcher.find();
        if(matchFound) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean validateEmail(String email)
    {
        boolean isValid = false;
        Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.find();
        if(matchFound) {
            isValid = true;
        }
        return isValid;
    }
}
