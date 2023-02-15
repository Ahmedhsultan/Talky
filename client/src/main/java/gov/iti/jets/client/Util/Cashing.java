package gov.iti.jets.client.Util;

import gov.iti.jets.client.Queues.MyID;
import gov.iti.jets.common.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Cashing {
    public static void cash(){
        encodeAndWriteFile(MyID.getInstance().getMyId(), MyID.getInstance().getPassword());
    }

    public static String[] getCash(){
        return decodeAndReadFile();
    }

    private static void encodeAndWriteFile(String UserId, String password){
        String originalString = UserId + " " + password;
        byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes());
        String encodedString = new String(encodedBytes);

        try (FileOutputStream fos = new FileOutputStream(Constants.MAIN_DIR + "\\" + Constants.CASH_FILE_NAMW)) {
            fos.write(encodedString.getBytes());
            System.out.println("Encoded string saved to file in "+ Constants.MAIN_DIR + "\\" + Constants.CASH_FILE_NAMW);
        } catch (Exception e) {
            System.out.println("Error while saving encoded string to file: " + e.getMessage());
        }
    }

    private static String[] decodeAndReadFile() {
        //Get file from path
        File file = new File(Constants.MAIN_DIR + "\\" + Constants.CASH_FILE_NAMW);
        //check if exist if not return null thats mean no data cashed
        if (!file.exists())
            return null;

        byte[] encodedBytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            encodedBytes = new byte[(int) file.length()];
            fis.read(encodedBytes);
        } catch (
                IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
            return null;
        }

        byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
        String decodedString = new String(decodedBytes);
        System.out.println("Decoded string: " + decodedString);
        return decodedString.split(" ");
    }
}
