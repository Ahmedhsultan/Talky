package gov.iti.jets.server.service;

import gov.iti.jets.common.dto.ConnectionDto;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.entity.statistics.CountryStat;
import gov.iti.jets.server.entity.statistics.GenderStat;
import gov.iti.jets.server.entity.statistics.UserStatusStat;
import gov.iti.jets.server.persistence.ServerDao;
import gov.iti.jets.server.persistence.dao.UserDao;

import java.rmi.RemoteException;
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
}
