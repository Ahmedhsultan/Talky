package gov.iti.jets.common.util;


import gov.iti.jets.common.dto.ConnectionDto;

import java.util.ArrayList;

public class ConnectedClientsList extends ArrayList<ConnectionDto> {
    private static ConnectedClientsList connectedClientsList;
    public static ConnectedClientsList getList(){
        if(connectedClientsList == null)
            connectedClientsList = new ConnectedClientsList();

        return connectedClientsList;
    }
}
