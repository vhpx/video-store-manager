package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.VSMApplication;
import com.guccigang.videostoremanager.core.Constant;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneController {
    private static SceneController instance;
    private String currentScene;

    private Stage stage;
    private final HashMap<String, Scene> scenes = new HashMap<>();

    private SceneController() {
        // Singleton
    }

    public static SceneController getInstance() {
        if (instance == null)
            instance = new SceneController();

        return instance;
    }

    public void link(Stage stage) {
        this.stage = stage;
    }

    public void add(String name, Scene scene) {
        if (scenes.containsKey(name))
            throw new IllegalArgumentException("Scene with name " + name + " already exists");

        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Scene name cannot be null or empty");

        if (scene == null)
            throw new IllegalArgumentException("Scene cannot be null");

        if (scenes.isEmpty())
            currentScene = name;

        scenes.put(name, scene);
    }

    public void showScene(String name) {
        if (stage == null)
            throw new IllegalStateException("SceneController has not been linked to a stage.");

        if (!scenes.containsKey(name))
            throw new IllegalArgumentException("No scene with name " + name);

        stage.setScene(scenes.get(name));
    }

    public Scene getSceneByName(String name) {
        if (!scenes.containsKey(name))
            throw new IllegalArgumentException("No scene with name " + name);

        return scenes.get(name);
    }

    public Scene getCurrentScene() {
        return scenes.get(currentScene);
    }

    private Scene getScene(VSMApplication app, String viewName) throws IOException {
        var filePath = viewName + ".fxml";

        // Get the resource url from the provided application instance
        var resourceUrl = app.getClass().getResource(filePath);

        if (resourceUrl == null)
            throw new IllegalArgumentException("No view with name " + viewName);

        // Load the scene using FXMLLoader
        var fxmlLoader = new FXMLLoader(resourceUrl);
        return new Scene(fxmlLoader.load());
    }

    public void initialize(VSMApplication app) throws IOException {
        // Load scenes
        for (var sceneName : Constant.getSceneNames()) {
            var scene = getScene(app, sceneName);
            add(sceneName, scene);
        }

        showScene(Constant.getDefaultSceneName());
    }

    public Scene getDefaultScene() {
        return getSceneByName(Constant.getDefaultSceneName());
    }
}
