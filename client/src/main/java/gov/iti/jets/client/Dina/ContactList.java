package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.dto.MessageDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactList {
    private static ObservableList<ContactDto> contactList;
    public static ObservableList<ContactDto> getList(){
        if(contactList == null)
            contactList = FXCollections.observableArrayList();

        return contactList;
    }
}
