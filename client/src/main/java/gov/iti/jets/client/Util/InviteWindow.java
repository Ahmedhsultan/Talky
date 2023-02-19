package gov.iti.jets.client.Util;

import gov.iti.jets.client.Queues.MyID;
import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.network.service.InvitationService;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.dto.Test;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.common.network.server.ServerInvitation;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class InviteWindow {
    public InviteWindow(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Invite");
        window.setMinWidth(250);

        Pane invitationView = PaneManager.getPaneManager().putInvitationView();
        ((Button) invitationView.lookup("#invite")).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Code to handle the button click event
                try {
                    String reciverId = ((TextField) invitationView.lookup("#number")).getText().trim();

                    ServerInvitation serverInvitation = RMIManager.lookUpInvitation();
                    serverInvitation.sendInvitation(MyID.getInstance().getMyId(),reciverId);
                    new AlertWindow(reciverId + " has invited successful");
                } catch (RemoteException | NotBoundException e) {
                    String msg = e.getMessage().split(":")[2];
                    new AlertWindow(msg);
                    throw new RuntimeException(e);
                }
            }
        });
        Text text = ((Text) invitationView.lookup("#close"));
        text.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Code to handle the button click event
                Stage window = (Stage) text.getScene().getWindow();
                window.close();
            }
        });


        Scene scene = new Scene(invitationView);
        window.setScene(scene);
        window.showAndWait();
    }
}
