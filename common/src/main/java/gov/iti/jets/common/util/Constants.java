package gov.iti.jets.common.util;


import com.google.common.hash.Hashing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Constants {

    //Validation Patterns
    public static final String PHONE_PATTERN = "^[01][0-9]{10}$";
    public static final String PASSWORD_PATTERN = ".";
    public static final String NAME_PATTERN = "^[a-zA-Z]{3,20}";
    public static final String EMAIL_PATTERN ="^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";


    //Online status if he is not offline then he is online
    public static final String ONLINE_STATUS_AVAILABLE = "available";
    public static final String ONLINE_STATUS_BUSY = "busy";
    public static final String ONLINE_STATUS_AWAY = "away";
    public static final String ONLINE_STATUS_OFFLINE = "offline";


    //Validation Theme
    public static final String ERROR_BORDER_RED = "-fx-border-color: red; -fx-border-width: 2px;";
    public static final String CORRECT_INPUT = "-fx-border-color: grey; -fx-border-width: 0px; ";
    public static final String RED_FONT = "-fx-text-fill: #FF0000;";

    //Validation Sentence
    public static final String FIELD_EMPTY = "field can't be empty";

    //Paths

    //server
    public static final String MAIN_DIR = System.getProperty("user.dir");
    public static final String USER_IMAGES_DIR =  MAIN_DIR +"/images\\users\\";
    public static final String CHAT_IMAGES_DIR =  MAIN_DIR +"\\images\\chats\\";

    //client
    public static final String CHAT_FILES_DIR =  MAIN_DIR +"\\files\\";

    //Default buffer size
    public static final int DEFAULT_BUFFER_SIZE = 5_000_000 ;




    public static String hashPassword(String input) {
        String sha256hex = Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
        return  sha256hex;
    }
    public static byte[] imageToByteArray(String path) throws IOException
    {
        BufferedImage bImage = ImageIO.read(new File(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String tokens[] = path.split("[.]",0);
        ImageIO.write(bImage, tokens[tokens.length-1], bos );
        byte [] data = bos.toByteArray();
        return data;
    }
    public static File byteArrayToImage(byte[] data, String path) throws IOException
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        File f = new File(path) ;
        String tokens[] = path.split("[.]",0);
        ImageIO.write(bImage2, tokens[tokens.length-1], f);
        System.out.println("image created");
        return f;
    }
}
