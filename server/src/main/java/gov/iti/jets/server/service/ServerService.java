package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ConnectionDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.Util.Queues.StatsLists;
import gov.iti.jets.server.controller.ConnectionController;
import gov.iti.jets.server.controller.IServerController;
import gov.iti.jets.server.controller.InvitationController;
import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.entity.statistics.CountryStat;
import gov.iti.jets.server.entity.statistics.GenderStat;
import gov.iti.jets.server.entity.statistics.UserStatusStat;
import gov.iti.jets.server.network.RMIManager;
import gov.iti.jets.server.persistence.ServerDao;
import gov.iti.jets.server.persistence.dao.UserDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.chart.PieChart;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ServerService {

    private ServerDao serverDao;
    private Thread checkClientConnectionThread;

    public ServerService() {
        serverDao = new ServerDao();
    }

    public GenderStat getGenderStats() {
        GenderStat genderStat = null;
        long males = serverDao.getNumberOfMales();
        long total = serverDao.getNumberOfUsers();
        if (total != -1 && males != -1) {
            genderStat = new GenderStat();
            genderStat.setNumOfMales(males);
            genderStat.setTotal(total);
            genderStat.setNumOfFemales(total - males);
        }
        return genderStat;

    }

    public UserStatusStat getUserStatusStats() {
        UserStatusStat stat = null;
        long numOfOffline = serverDao.getNumberOfOfflineUsers();
        long total = serverDao.getNumberOfUsers();
        if (total != -1 && numOfOffline != -1) {
            stat = new UserStatusStat();
            stat.setNumOfOffline(numOfOffline);
            stat.setTotal(total);
            stat.setNumOfOnline(total - numOfOffline);
        }
        return stat;

    }

    public CountryStat getUserCountryStats() {
        CountryStat stat = serverDao.getCountryStats();
        return stat;

    }

    public void sendAnnouncement(String message) throws RemoteException {

                for (Map.Entry<String, ConnectionDto> entry : ConnectedClientsMap.getList().entrySet()) {
                        entry.getValue().getIClient().receiveAnnouncement(message);
                }
    }

    public void startServer() {
        try {
            Registry reg = RMIManager.getRegistry();
            reg.rebind("register", new UserController());
            reg.rebind("iserver", new IServerController());
            reg.rebind("connection", new ConnectionController());
            reg.rebind("invitation", new InvitationController());
             checkClientConnectionThread = new Thread(new CheckConnectedClientStatus());
            checkClientConnectionThread.start();
//            AnnouncementList.getInstance().getList().addListener(new ListChangeListener<String>() {
//                public void onChanged(ListChangeListener.Change<? extends String> change) {
//                    try {
//                        System.out.println("send");
//                        new ServerService().sendAnnouncement(AnnouncementList.getInstance().getList().get(0));
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }});

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    public void stopServer() {
        try {
            Registry reg = RMIManager.getRegistry();
            reg.unbind("register");
            reg.unbind("iserver");
            reg.unbind("connection");
            reg.unbind("invitation");
            RMIManager.removeRegistry();
            checkClientConnectionThread.stop();
            if (UnicastRemoteObject.unexportObject(reg, true)) {
                System.out.println("Registry removed!");
            } else {
                System.out.println("Registry can not be removed.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
//    public List<UserDto>  getOnlineUsers()
//    {
//        List<UserDto> onlineDtos = new ArrayList<>();
//                for(ConnectionDto connectionDto:ConnectedClientsMap.getList().values())
//                {
//                    onlineDtos.add(connectionDto.getUserDto());
//                }
//        return onlineDtos;
//    }
//    public List<UserDto>getOfflineUsers()
//    {
//        List<UserDto> offlineDtos = new UserService().getOfflineUsers();
//        return offlineDtos;
//    }

}
