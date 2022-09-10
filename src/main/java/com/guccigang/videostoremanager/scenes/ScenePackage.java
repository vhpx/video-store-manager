package com.guccigang.videostoremanager.scenes;

public class ScenePackage {
    final String sceneName;
    final String sceneFileName;
    final String cssFileName;

    public ScenePackage(String sceneName, String cssFileName, String sceneFileName) {
        this.sceneName = sceneName;
        this.cssFileName = cssFileName;
        this.sceneFileName = sceneFileName;
    }

    public ScenePackage(String sceneName, String cssFileName) {
        this(sceneName, cssFileName, sceneName + ".fxml");
    }

    public ScenePackage(String sceneName) {
        this(sceneName, null);
    }

    public String getSceneName() {
        return sceneName;
    }

    public String getSceneFileName() {
        return sceneFileName;
    }

    public String getCssFileName() {
        return cssFileName;
    }
}
