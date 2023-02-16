package gov.iti.jets.server.service;


import gov.iti.jets.common.dto.ConnectionDto;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.persistence.dao.UserDao;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;

public class CheckConnectedClientStatus implements Runnable{
    @Override
    public void run() {
        while (true){
            UserDao userDao = new UserDao();
//            for (ConnectionDto connectionDto : ConnectedClientsMap.getList().values()) {
            Iterator<ConnectionDto> iterator = ConnectedClientsMap.getList().values().iterator();
            while (iterator.hasNext()) {
                ConnectionDto connectionDto = iterator.next();
                try {
                    connectionDto.getIClient().receive();
                    userDao.setOnlineStatus(connectionDto.getUserDto().getId(),Constants.ONLINE_STATUS_AVAILABLE);
                    System.out.println( connectionDto.getUserDto().getId() + Constants.ONLINE_STATUS_AVAILABLE);
                }catch (RemoteException ex){
                    try {
                        if (ConnectedClientsMap.getList().containsKey(connectionDto.getUserDto().getId())){
//                            ConnectedClientsMap.getList().remove(connectionDto.getUserDto().getId());
                            iterator.remove();
                            userDao.setOnlineStatus(connectionDto.getUserDto().getId(), Constants.ONLINE_STATUS_OFFLINE);
                            System.out.println( connectionDto.getUserDto().getId() + Constants.ONLINE_STATUS_OFFLINE);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
