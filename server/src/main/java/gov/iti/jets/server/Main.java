package gov.iti.jets.server;


import gov.iti.jets.server.Util.Queues.StatsLists;
import gov.iti.jets.server.controller.ConnectionController;
import gov.iti.jets.server.controller.IServerController;
import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.network.RMIManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import gov.iti.jets.server.service.ServerService;

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
//        Thread th = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ServerService serverService = new ServerService();
//                    StatsLists.onlineList =  FXCollections.observableArrayList(
//                            new PieChart.Data("Online",serverService.getUserStatusStats().getNumOfOnline()),
//                            new PieChart.Data("Offline",serverService.getUserStatusStats().getNumOfOffline())
//                    );
//            Registry reg = RMIManager.getRegistry();
//            reg.rebind("register", new UserController());
//            reg.rebind("server", new IServerController());
//            while(true)
//            {
//
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//            }
//        });
//        th.start();

        launch();


    }


}
