package gov.iti.jets;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight() - 60;
        Parent root = FXMLLoader.load(getClass().getResource("/views/MainScene.fxml"));
        stage.setTitle("Registration");
        stage.setResizable(false);
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}