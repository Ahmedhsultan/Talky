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

    public static void main(String[] args) {
        launch();
    }


}
