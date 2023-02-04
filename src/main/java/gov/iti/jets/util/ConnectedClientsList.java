package gov.iti.jets.util;

import gov.iti.jets.dto.ConnectionDto;
import java.util.ArrayList;

public class ConnectedClientsList extends ArrayList<ConnectionDto> {
    private static ConnectedClientsList connectedClientsList;
    public static ConnectedClientsList getList(){
        if(connectedClientsList == null)
            connectedClientsList = new ConnectedClientsList();

        return connectedClientsList;
    }
}
