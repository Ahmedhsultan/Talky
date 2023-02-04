package gov.iti.jets;


import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.network.service.RegisterService;
import gov.iti.jets.util.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight() - 60;
        Parent root = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/views/MainScene.fxml"));
        stage.setTitle("Registration");
        stage.setResizable(false);
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
//        RegisterService reg;
//        reg = new RegisterService();
//        UserDto user = new UserDto();
//        user.setId("01090780888");
//        user.setImgPath("01078965432.png");
//
//        try {
//
//            user.setImage(imageToByteArray("C:\\Users\\hp\\Pictures\\test.png"));
//            System.out.println("success ");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        user.setName("Dina");
//        UserRegistrationDto x = new UserRegistrationDto(user,"Amany12345");
//        reg.addUser(x);
//    }
    }
}