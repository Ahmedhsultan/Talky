package gov.iti.jets.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Constants {

    //Validation Patterns
    public static final String PHONE_PATTERN = "^[01][0-9]{10}$";
    public static final String PASSWORD_PATTERN = "([a-z][A-Z][0-9]){8,20}$";
    public static final String NAME_PATTERN = "^[a-zA-Z ]{3,20}$";
    public static final String EMAIL_PATTERN ="^(.+)@(\\S+)$";


    //Online status if he is not offline then he is online
    public static final String ONLINE_STATUS_AVAILABLE = "available";
    public static final String ONLINE_STATUS_BUSY = "busy";
    public static final String ONLINE_STATUS_AWAY = "away";
    public static final String ONLINE_STATUS_OFFLINE = "offline";



    public static String hashPassword(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
