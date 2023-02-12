package gov.iti.jets.server;


import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.network.RMIManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view\\ServerSlider.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // public static boolean createDirectory(String serverpath) throws RemoteException {
    //     File serverpathdir = new File(serverpath);
    //     return serverpathdir.mkdir();

    // }
    public static void main(String[] args) {
//        try {
//            createDirectory("E:\\hi");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        try {
//            Registry reg = RMIManager.getRegistry();
//            reg.rebind("register", new UserController());
//            while(true)
//            {
//
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Hello World!");
        launch();
    }


}
