package com.guccigang.videostoremanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VSMController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}