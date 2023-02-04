package gov.iti.jets;


import gov.iti.jets.callBack.CheckConnection;
import gov.iti.jets.dto.ConnectionDto;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.dto.registration.UserRegistrationDto;
import gov.iti.jets.network.IConnection;
import gov.iti.jets.network.UserRemote;
import gov.iti.jets.network.service.RegisterService;
import gov.iti.jets.util.ConnectionFlag;
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//public class Main  {
////    @Override
////    public void start(Stage stage) throws IOException {
////
////        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
////        double width = screenSize.getWidth();
////        double height = screenSize.getHeight() - 60;
////        Parent root = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
//////        Parent root = FXMLLoader.load(getClass().getResource("/views/MainScene.fxml"));
////        stage.setTitle("Registration");
////        stage.setResizable(false);
////        stage.setScene(new Scene(root, width, height));
////        stage.show();
////    }
//    public static void main(String[] args) {
////        launch();
////        RegisterService reg;
////        reg = new RegisterService();
//        UserDto user = new UserDto();
//        user.setId("01078965432");
////        user.setImgPath("01078965432.png");
////        try {
////            user.setImage(imageToByteArray("C:/Users/hp/Pictures/Camera Roll/test.png"));
////            System.out.println("success");
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//        user.setName("Amany");
////        UserRegistrationDto x = new UserRegistrationDto(user,"Amany12345");
////        reg.addUser(x);
//
//        Registry registry = null;
//        try {
//            registry = LocateRegistry.getRegistry("localhost",1099);
//            IConnection obj = (IConnection) registry.lookup("connection");
//            ConnectionDto connectionDto = new ConnectionDto();
//            connectionDto.setUserDto(user);
//            connectionDto.setIClient(new CheckConnection());
//            obj.connect(connectionDto);
//            while (true){
//                Thread.sleep(40000);
//            }
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        } catch (NotBoundException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
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
    }
}