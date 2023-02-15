package gov.iti.jets.server.controller;


import gov.iti.jets.common.dto.ConnectionDto;
import gov.iti.jets.common.network.server.IConnection;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.Util.Queues.StatsLists;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionController extends UnicastRemoteObject implements IConnection {
    public ConnectionController() throws RemoteException {
        super();
    }

    @Override
    public void connect(ConnectionDto connectionDto) throws RemoteException{
//        Platform.runLater(()-> {
            ConnectedClientsMap.getList().put(connectionDto.getUserDto().getId(), connectionDto);
            StatsLists.getInstance().updateUserStats();
//                          });


    }

    @Override
    public void disConnect(String userId) throws RemoteException{
//        Platform.runLater(()-> {
            if(ConnectedClientsMap.getList().containsKey(userId))
                ConnectedClientsMap.getList().remove(userId);
            StatsLists.getInstance().updateUserStats();
//        });

    }
}
