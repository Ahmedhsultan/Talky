package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.MessageDto;

import java.util.HashMap;

public class ContactList extends HashMap<Integer, MessageDto> {
    private static ContactList contactList;
    public static ContactList getList(){
        if(contactList == null)
            contactList = new ContactList();

        return contactList;
    }
}
