package gov.iti.jets.client;

import gov.iti.jets.client.Util.Cashing;
import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.client.network.service.LoginService;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.rmi.RemoteException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setWidth(1104);
        stage.setHeight(534);

        SceneManager s =SceneManager.getSceneManager();
        s.initStage(stage);
        s.switchToLoginScene();

        String[] cashed = Cashing.getCash();
        if (cashed != null){
            try {
                LoginService loginService = new LoginService();
                loginService.login(cashed[0],cashed[1]);
                s.switchToChatScene();
            }catch (RemoteException ex){
                s.switchToLoginScene();
            }
        }
    }
    public static void main() {
        launch();
    }
}
