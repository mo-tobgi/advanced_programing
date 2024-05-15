package com.example.phase2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.Serializable;

public class HelloController implements Serializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}