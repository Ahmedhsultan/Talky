package gov.iti.jets.server;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import gov.iti.jets.server.controller.IServerController;
import gov.iti.jets.server.controller.InvitationController;

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
//        try {
//            createDirectory("E:\\hi");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        try {
//            Registry reg = RMIManager.getRegistry();
//            reg.rebind("register", new UserController());
        try {
            Registry reg = RMIManager.getRegistry();
            reg.rebind("register", new UserController());
            reg.rebind("iserver", new IServerController());
            reg.rebind("invitation", new InvitationController());
//            while(true)
//            {
//
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Hello World!");
        launch();
        ServerService service = new ServerService();
        System.out.println(service.getGenderStats());
        System.out.println(service.getUserStatusStats());
        System.out.println(service.getUserCountryStats());
//public class Main {
//
//    public static void main(String[] args) {
////        try {
//            ChatterBotFactory factory = new ChatterBotFactory();
//
//
//            ChatterBot bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
//            ChatterBotSession botsession = bot.createSession();
//
//            while (true) {
//                String s = "Hi";
//
//                Scanner sc = new Scanner(System.in);
//                s= sc.nextLine();
//                System.out.println("bot1> " + s);
//
//                s = botsession.think(s);
//                System.out.println("bot2> " + s);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
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

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
        launch();
    }


}
