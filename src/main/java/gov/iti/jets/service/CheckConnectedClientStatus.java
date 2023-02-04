package gov.iti.jets.service;

import gov.iti.jets.dto.ConnectionDto;
import gov.iti.jets.dto.UserDto;
import gov.iti.jets.network.IClient;
import gov.iti.jets.persistence.dao.UserDao;
import gov.iti.jets.util.ConnectedClientsList;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class CheckConnectedClientStatus implements Runnable{
    @Override
    public void run() {
        while (true){
            UserDao userDao = new UserDao();
            for (ConnectionDto connectionDto : ConnectedClientsList.getList()) {
                try {
                    connectionDto.getIClient().receive();
                    userDao.setOnlineStatus(connectionDto.getUserDto().getId(),"Online");
                    System.out.println( connectionDto.getUserDto().getId() + "Online");
                }catch (RemoteException ex){
                    try {
                        ConnectedClientsList.getList().remove(connectionDto);
                        userDao.setOnlineStatus(connectionDto.getUserDto().getId(),"Offline");
                        System.out.println( connectionDto.getUserDto().getId() + "Offline");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
