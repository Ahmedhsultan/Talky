package gov.iti.jets.common.util;

import gov.iti.jets.common.dto.MessageDto;
import java.util.HashMap;

public class ChatsMapList extends HashMap<Integer, MessageDto> {
    private static ChatsMapList chatsMapList;
    public static ChatsMapList getList(){
        if(chatsMapList == null)
            chatsMapList = new ChatsMapList();

        return chatsMapList;
    }
}
