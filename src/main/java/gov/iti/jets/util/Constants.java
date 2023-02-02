package gov.iti.jets.util;

import com.google.common.hash.Hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Constants {

    //Validation Patterns
    public static final String PHONE_PATTERN = "^[01][0-9]{10}$";
    public static final String PASSWORD_PATTERN = "([a-zA-Z0-9]){8,20}$";
    public static final String NAME_PATTERN = "^[a-zA-Z ]{3,20}$";
    public static final String EMAIL_PATTERN ="^(.+)@(\\S+)$";


    //Online status if he is not offline then he is online
    public static final String ONLINE_STATUS_AVAILABLE = "available";
    public static final String ONLINE_STATUS_BUSY = "busy";
    public static final String ONLINE_STATUS_AWAY = "away";
    public static final String ONLINE_STATUS_OFFLINE = "offline";



    public static String hashPassword(String input) {
        String sha256hex = Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
        return  sha256hex;

    }
}
