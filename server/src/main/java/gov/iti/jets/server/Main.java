package gov.iti.jets.server;


import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.network.RMIManager;
import gov.iti.jets.server.service.ServerService;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        try {
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
        try {
            Registry reg = RMIManager.getRegistry();
            reg.rebind("register", new UserController());
            while(true)
            {

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        ServerService service = new ServerService();
        System.out.println(service.getGenderStats());
        System.out.println(service.getUserStatusStats());
        System.out.println(service.getUserCountryStats());

        System.out.println("Hello World!");
    }
}
