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
}
