package gov.iti.jets.client.business.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PaneManager {

    private static final PaneManager paneManager =new PaneManager();
    private static  Map<String, Pane> primaryPanes = new HashMap<>();

    private Pane pane;

    public static PaneManager getPaneManager(){
        return paneManager;
    }

    public static void setPrimaryPane(String name, Pane pane) {
        primaryPanes.put(name, pane);
    }

    public Pane putLoginPasswordPane() {
        loadPane("LoginPassword","login");
        return pane;
    }

    public Pane putLoginPhonePane() {
        loadPane("LoginPhone","login");
        return pane;
    }

    public Pane putRecentChatCard() {
        loadPane("chatCard");
        return pane;
    }

    public Pane putContactCard() {
        loadPane("contactCard");
        return pane;
    }

    public Pane putInvitationCard() {
        loadPane("invitationCard");
        return pane;
    }

    public Pane putNotificationPane() {
        loadPane("notificationCard");
        return pane;
    }

    public Pane putProfilePane() {
        loadPane("Profile");
        return pane;
    }
    public Pane putMenuPane() {
        loadPane("menu", "containerPane");
        return pane;
    }
    private void loadPane(String loadedPane, String primaryPane) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(String.format("/views/%s.fxml", loadedPane)));
                pane = (Pane) root;
                putPaneContents(primaryPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void putPaneContents(String primaryPane) {
        primaryPanes.get(primaryPane).getChildren().clear();
        primaryPanes.get(primaryPane).getChildren().addAll(pane.getChildren());
        primaryPanes.get(primaryPane).getChildren().forEach(n -> {
            n.setLayoutY(n.getLayoutY()-10);
            n.setLayoutX(n.getLayoutX()-10);
        });
    }

    private void loadPane(String loadedPane) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(String.format("/views/%s.fxml", loadedPane)));
            pane = (Pane) root;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
