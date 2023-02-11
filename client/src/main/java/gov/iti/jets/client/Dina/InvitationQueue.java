package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.ContactDto;

import java.util.ArrayList;

public class InvitationQueue extends ArrayList<ContactDto> {
    private static InvitationQueue invitationQueue;
    public static InvitationQueue getList(){
        if(invitationQueue == null)
            invitationQueue = new InvitationQueue();

        return invitationQueue;
    }
}
