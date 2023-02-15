package gov.iti.jets.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view\\ServerSlider.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



//package gov.iti.jets.server;
//
//
//import gov.iti.jets.server.controller.ConnectionController;
//import gov.iti.jets.server.controller.IServerController;
//import gov.iti.jets.server.controller.InvitationController;
//import gov.iti.jets.server.controller.UserController;
//import gov.iti.jets.server.network.RMIManager;
//
//import java.io.File;
//import java.rmi.RemoteException;
//import java.rmi.registry.Registry;
//
//public class Main {
//    public static boolean createDirectory(String serverpath) throws RemoteException {
//        File serverpathdir = new File(serverpath);
//        return serverpathdir.mkdir();
//
//    }
//    public static void main(String[] args) {
//        try {
//            Registry reg = RMIManager.getRegistry();
//
//            reg.rebind("register", new UserController());
//            reg.rebind("iserver", new IServerController());
//            reg.rebind("connection", new ConnectionController());
//            reg.rebind("invitation", new InvitationController());
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Hello World!");
//    }
//}
