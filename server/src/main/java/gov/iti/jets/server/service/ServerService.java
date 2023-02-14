package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ConnectionDto;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.Util.Queues.StatsLists;
import gov.iti.jets.server.controller.ConnectionController;
import gov.iti.jets.server.controller.IServerController;
import gov.iti.jets.server.controller.UserController;
import gov.iti.jets.server.entity.statistics.CountryStat;
import gov.iti.jets.server.entity.statistics.GenderStat;
import gov.iti.jets.server.entity.statistics.UserStatusStat;
import gov.iti.jets.server.network.RMIManager;
import gov.iti.jets.server.persistence.ServerDao;
import gov.iti.jets.server.persistence.dao.UserDao;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ServerService {

    private ServerDao serverDao;
    private UserDao userDao;

    public ServerService() {
        serverDao = new ServerDao();
        userDao = new UserDao();
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

        for (Map.Entry<String, ConnectionDto> entry : ConnectedClientsMap.getList().entrySet())
            entry.getValue().getIClient().receiveAnnouncement(message);
    }

    public void startServer() {
        try {
            Registry reg = RMIManager.getRegistry();
            reg.rebind("register", new UserController());
            reg.rebind("iserver", new IServerController());
            reg.rebind("connection", new ConnectionController());

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
            RMIManager.removeRegistry();
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

}
