package gov.iti.jets.client;


import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.common.dto.UserDto;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

//        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
//        double width = screenSize.getWidth();
//        double height = screenSize.getHeight() - 60;
//        Parent root = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
////        Parent root = FXMLLoader.load(getClass().getResource("/views/MainScene.fxml"));
//        stage.setTitle("Registration");
//        stage.setResizable(false);
//        stage.setScene(new Scene(root, width, height));
//        stage.show();
        SceneManager s =SceneManager.getSceneManager();
        s.initStage(stage);
        s.switchToLoginScene();

    }
    public static void main(String[] args) {
        launch();
//        RegisterService reg;
//        reg = new RegisterService();
        UserDto user = new UserDto();
        user.setId("01090780888");
        user.setImgPath("01078965432.png");
//
//        try {
//
//            user.setImage(imageToByteArray("C:\\Users\\hp\\Pictures\\test.png"));
//            System.out.println("success ");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        user.setName("Dina");
//        UserRegistrationDto x = new UserRegistrationDto(user,"Amany12345");
//        reg.addUser(x);
//    }

        /**
         * Starting online and offline service
         */
//        Registry registry = null;
//        try {
//            registry = RMIManager.getRegistry();
//            IConnection obj = (IConnection) registry.lookup("connection");
//            ConnectionDto connectionDto = new ConnectionDto();
//            connectionDto.setUserDto(user);
//            connectionDto.setIClient(new CheckConnection());
//            obj.connect(connectionDto);
//            while (true){
//                Thread.sleep(40000);
//            }
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        } catch (NotBoundException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
