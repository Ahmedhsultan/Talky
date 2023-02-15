package gov.iti.jets.client.Queues;

import gov.iti.jets.common.dto.ContactDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContactList {
    private static ObservableList<ContactDto> contactList;
    public static ObservableList<ContactDto> getList(){
        if(contactList == null)
            contactList = FXCollections.observableArrayList();

        return contactList;
    }
}
