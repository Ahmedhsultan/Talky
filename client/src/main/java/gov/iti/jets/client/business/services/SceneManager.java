package gov.iti.jets.client.business.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private static SceneManager sceneManager;
    private Stage primaryStage;
    private Map<String, Scene> scenes = new HashMap<>();

    private SceneManager(){}

    public static SceneManager getSceneManager(){
        if(sceneManager == null){
            sceneManager  = new SceneManager();

        }
        return sceneManager;
    }

    public void clear(){
        scenes.clear();
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
            System.out.println("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        else if (!scenes.containsKey(name)) {
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(String.format("views/%s.fxml", name)));
                Scene scene = new Scene(root);
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
