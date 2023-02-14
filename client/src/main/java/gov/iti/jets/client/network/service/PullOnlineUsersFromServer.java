package gov.iti.jets.client.network.service;

import gov.iti.jets.client.Util.AlertWindow;
import gov.iti.jets.client.Util.ReconnectWindow;
import gov.iti.jets.common.network.server.IServer;
import javafx.application.Platform;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PullOnlineUsersFromServer {
    private static PullOnlineUsersFromServer pullOnlineUsersFromServer;
    private IServer iServer;
    private Thread thread ;
    private PullOnlineUsersFromServer(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
                while (true){
                    try {
                        Thread.sleep(10000);
                        iServer = getIserver();
//                        iServer.getOnlineUsers(MyID.getInstance().getMyId());
                    } catch (InterruptedException e) {
                        System.out.println("dis");
                        e.printStackTrace();
                    } /*catch (RemoteException e) {
                        e.printStackTrace();
                        iServer = getIserver();
                    }*/
                }
            }
        });
        thread.start();
    }

    private static IServer getIserver(){
        IServer iServer;
        try {
            iServer = RMIManager.lookUpIServer();
            return iServer;
        } catch (RemoteException | NotBoundException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ReconnectWindow.getInstance("Server is disconnected, please Reconnect");
                }
            });
        }
        return null;
    }

    public void stopService(){
        System.out.println("stop pulling");
        thread.interrupt();
    }

    public static PullOnlineUsersFromServer getInstance(){
        if (pullOnlineUsersFromServer == null)
            pullOnlineUsersFromServer = new PullOnlineUsersFromServer();
        return pullOnlineUsersFromServer;
    }
}
