package gov.iti.jets.server.Util.Queues;


import gov.iti.jets.common.dto.ConnectionDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;

public class ConnectedClientsMap {
    private static ObservableMap<String,ConnectionDto> connectedClientsMap;
    public static ObservableMap<String,ConnectionDto> getList(){
        if(connectedClientsMap == null)
            connectedClientsMap = FXCollections.observableHashMap();

        return connectedClientsMap;
    }
}
