package gov.iti.jets.business.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private static final SceneManager sceneManager =new SceneManager();
    private Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();

    public static SceneManager getSceneManager(){
        return sceneManager;
    }


    public void initStage(Stage stage) {
        if (primaryStage != null) {
            throw new IllegalArgumentException("The Stage Already been initialized");
        }
        primaryStage = stage;
    }

    public void switchToRegistrationScene() {
        String sceneName = "Register";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToChatScene() {
        String sceneName = "Chat";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void switchToLoginScene() {
        String sceneName = "Login";
        primaryStage.setTitle(sceneName);
        loadView(sceneName);
    }

    public void loadView(String name) {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey(name)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(String.format("/views/%s.fxml", name)));
                Scene scene = new Scene(root, 1200, 650);
                scenes.put(name, scene);
                primaryStage.setResizable(false);
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            primaryStage.setScene(scenes.get(name));
        }
    }
}
