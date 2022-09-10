package com.guccigang.videostoremanager.scenes;

import com.guccigang.videostoremanager.VSMApplication;
import com.guccigang.videostoremanager.core.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SceneManager {
    private VSMApplication app;
    private Stage stage;
    private final HashMap<String, Scene> scenes = new HashMap<>();

    public void link(VSMApplication app, Stage stage) {
        // Assert that the application and stage are not null
        if (app == null || stage == null)
            throw new IllegalArgumentException("The application and stage cannot be null.");

        // Assert that the application and stage are not already linked
        if (this.app != null || this.stage != null)
            throw new IllegalStateException("The application and stage are already linked.");

        // Link the application and stage
        this.app = app;
        this.stage = stage;

        // Notify the user that the application is ready
        System.out.println("SYSTEM: Linked application and stage to scene controller.\n");
    }

    public void add(String name, Scene scene) {
        if (scenes.containsKey(name)) throw new IllegalArgumentException("Scene with name " + name + " already exists");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Scene name cannot be null or empty");
        if (scene == null) throw new IllegalArgumentException("Scene cannot be null");
        scenes.put(name, scene);
    }

    public void showScene(String name) {
        if (stage == null) throw new IllegalStateException("SceneController has not been linked to a stage.");
        var scene = getSceneByName(name);
        stage.setScene(scene);
    }

    public Scene getSceneByName(String name) {
        // If the scene doesn't exist, throw an exception
        if (!scenes.containsKey(name)) throw new IllegalArgumentException("No scene with name " + name);

        // Else, return the scene
        return scenes.get(name);
    }

    private URL getResource(String filePath) {
        // If the file path is null or empty, throw an exception
        if (filePath == null || filePath.isEmpty())
            throw new IllegalArgumentException("File path cannot be null or empty");

        // Get the resource from the resources folder
        var resource = app.getClass().getResource(filePath);
        if (resource == null) throw new IllegalArgumentException("File " + filePath + " not found");

        // Return the available resource
        return resource;
    }

    private Scene getScene(String filePath) throws IOException {
        // Get the resource from the file path
        var resource = getResource(filePath);
        System.out.println("Loading scene from " + resource);

        // Load the scene using FXMLLoader
        var fxmlLoader = new FXMLLoader(resource);
        return new Scene(fxmlLoader.load());
    }

    private String getCSS(String filePath) {
        // Get the resource from the file path
        var resource = getResource(filePath);
        System.out.println("Loading CSS from " + resource);

        // Return the CSS file path
        return resource.toExternalForm();
    }

    public void initialize() throws IOException {
        // Throw an exception if the stage is null
        if (stage == null) throw new IllegalStateException("SceneController has not been linked to a stage.");

        // Throw an exception if the application is null
        if (app == null) throw new IllegalStateException("SceneController has not been linked to an application.");

        // Set the title of the stage
        String appTitle = Constants.getAppName();
        stage.setTitle(appTitle);

        // Load scenes
        for (var scenePkg : Constants.getScenesToLoad()) {
            // Get the scene's metadata
            String sceneName = scenePkg.getSceneName();
            String viewPath = scenePkg.getSceneFileName();
            String cssPath = scenePkg.getCssFileName();

            // Get the scene
            var scene = getScene(viewPath);

            // Get the scene's CSS if the CSS path is not null or empty
            var css = (cssPath == null || cssPath.isEmpty()) ? null : getCSS(cssPath);

            // If the scene has a css file, add it to the scene
            if (css != null) scene.getStylesheets().add(css);

            // Add the scene to the scene controller
            add(sceneName, scene);
        }

        // Load exit confirmation dialog
        stage.setOnCloseRequest(e -> {
            e.consume();
            showExitConfirmationDialog(stage);
        });

        // Show the default scene
        showScene(Constants.getDefaultScene());
        stage.show();
    }

    @FXML
    protected void showExitConfirmationDialog(Stage stage) {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        // Update the dialog's properties
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to exit the program.");
        alert.setContentText("Are you sure that your want to exit the program?");

        // Show the dialog and wait for the user's response
        if (alert.showAndWait().orElseThrow() == ButtonType.OK)
            // If the user clicked OK, close the stage
            stage.close();
    }

    public void closeWindow() {
        stage.close();
    }

    public Scene getCurrentScene() {
        return this.stage.getScene();
    }

}
