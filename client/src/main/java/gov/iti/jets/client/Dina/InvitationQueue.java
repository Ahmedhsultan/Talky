package gov.iti.jets.client.Dina;

import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.InvitationDto;

import java.util.ArrayList;

public class InvitationQueue extends ArrayList<InvitationDto> {
    private static InvitationQueue invitationQueue;
    public static InvitationQueue getList(){
        if(invitationQueue == null)
            invitationQueue = new InvitationQueue();

        return invitationQueue;
    }
}
