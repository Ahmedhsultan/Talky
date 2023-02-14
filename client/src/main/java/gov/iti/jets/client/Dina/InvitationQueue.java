package gov.iti.jets.client.Dina;
import gov.iti.jets.common.dto.InvitationDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InvitationQueue {
    private static ObservableList<InvitationDto> invitationQueue;
    public static ObservableList<InvitationDto> getList(){
        if(invitationQueue == null) {
            invitationQueue = FXCollections.observableArrayList();
        }
        return invitationQueue;
    }
}
