package gov.iti.jets.client.network.service;

import gov.iti.jets.client.Dina.MyID;
import gov.iti.jets.common.network.server.UserRemote;
import gov.iti.jets.common.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Base64;

public class LogoutService {
    public static void logout(boolean cash) throws NotBoundException, RemoteException {
        if(cash){
            encodeAndWriteFile(MyID.getInstance().getMyId(), MyID.getInstance().getPassword());
        }else{
            deleteCash();
        }
        UserRemote userRemote = RMIManager.lookUpRegister();
        userRemote.logout(MyID.getInstance().getMyId());
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
            System.out.println("Encoded string saved to file");
        } catch (Exception e) {
            System.out.println("Error while saving encoded string to file: " + e.getMessage());
        }
    }

    private static String[] decodeAndReadFile() {
        File file = new File(Constants.MAIN_DIR + "\\" + Constants.CASH_FILE_NAMW);
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
    private static void deleteCash(){
        File file = new File(Constants.MAIN_DIR + "\\" + Constants.CASH_FILE_NAMW);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Error while deleting file");
        }
    }
}
