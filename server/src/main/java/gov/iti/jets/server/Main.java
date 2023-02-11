package gov.iti.jets.server;


import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.network.RMIManager;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {
          String botname = "MyBot";
       // MagicParameters.readParameters();
        //PandorabotsAPI papi = new PandorabotsAPI(MagicParameters.hostname, MagicParameters.app_id, MagicParameters.user_key);
        try {
            ChatterBotFactory factory = new ChatterBotFactory();


            ChatterBot bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession botsession = bot.createSession();

            String s = "Hi";


            System.out.println("bot1> " + s);

            s = botsession.think(s);
            System.out.println("bot2> " + s);

        } catch (Exception e) {
            e.printStackTrace();

        }
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
        System.out.println("Hello World!");
    }
}
