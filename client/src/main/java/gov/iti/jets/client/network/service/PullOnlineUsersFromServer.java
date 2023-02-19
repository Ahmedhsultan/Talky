package gov.iti.jets.client.network.service;

import gov.iti.jets.client.Queues.ContactList;
import gov.iti.jets.client.Queues.MyID;
import gov.iti.jets.client.Queues.NotificationQueue;
import gov.iti.jets.client.Util.ReconnectWindow;
import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.NotificationDto;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.common.util.Constants;
import javafx.application.Platform;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class PullOnlineUsersFromServer {
    private static PullOnlineUsersFromServer pullOnlineUsersFromServer;
    private IServer iServer;
    private Thread thread ;
    private PullOnlineUsersFromServer(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(6000);
                        iServer = getIserver();
                        System.out.println("run");

                        NotificationDto notificationDto = new NotificationDto();
                        List<String> onlineList = iServer.getOnlineUsers(MyID.getInstance().getMyId());
                        for(var ele : ContactList.getList()){
                            if (onlineList.contains(ele.getId())){
                                if (ele.getIsOnlineStatus().equals(Constants.ONLINE_STATUS_OFFLINE)){
                                    System.out.println("on");
                                    ele.setIsOnlineStatus(Constants.ONLINE_STATUS_AVAILABLE);
                                    notificationDto.setName(ele.getName());
                                    notificationDto.setType("onlineStatus");
                                    notificationDto.setOnlineStatus(ele.getIsOnlineStatus());
                                    notificationDto.setBytes(ele.getImage());
                                    NotificationQueue.getList().add(notificationDto);
                                    var change = new ContactDto();
                                    ContactList.getList().add(change);
                                    ContactList.getList().remove(change);
                                }
                            }else{
                                if (ele.getIsOnlineStatus().equals(Constants.ONLINE_STATUS_AVAILABLE)){
                                    System.out.println("off");
                                    ele.setIsOnlineStatus(Constants.ONLINE_STATUS_OFFLINE);
                                    notificationDto.setType("onlineStatus");
                                    notificationDto.setName(ele.getName());
                                    notificationDto.setBytes(ele.getImage());
                                    notificationDto.setOnlineStatus(ele.getIsOnlineStatus());
                                    NotificationQueue.getList().add(notificationDto);
                                    var change = new ContactDto();
                                    ContactList.getList().add(change);
                                    ContactList.getList().remove(change);
                                }
                            }
                        }
                    } catch (InterruptedException | RemoteException e) {
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
