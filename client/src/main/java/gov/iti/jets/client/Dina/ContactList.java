package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.MessageDto;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactList extends ArrayList<ContactDto> {
    private static ContactList contactList;
    public static ContactList getList(){
        if(contactList == null)
            contactList = new ContactList();

        return contactList;
    }
}
