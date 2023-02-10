package gov.iti.jets.server.Util.Queues;


import gov.iti.jets.common.dto.ConnectionDto;

import java.util.HashMap;

public class ConnectedClientsMap extends HashMap<String,ConnectionDto> {
    private static ConnectedClientsMap connectedClientsMap;
    public static ConnectedClientsMap getList(){
        if(connectedClientsMap == null)
            connectedClientsMap = new ConnectedClientsMap();

        return connectedClientsMap;
    }
}
