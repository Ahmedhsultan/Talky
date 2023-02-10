package gov.iti.jets.server.Util.Queues;


import gov.iti.jets.common.dto.ConnectionDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectedClientsList extends HashMap<String,ConnectionDto> {
    private static ConnectedClientsList connectedClientsList;
    public static ConnectedClientsList getList(){
        if(connectedClientsList == null)
            connectedClientsList = new ConnectedClientsList();

        return connectedClientsList;
    }
}
