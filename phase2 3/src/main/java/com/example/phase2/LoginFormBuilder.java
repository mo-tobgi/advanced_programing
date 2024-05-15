package com.example.phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginFormBuilder {
    static VBox build(Stage primaryStage, SocialMediaApp app) {
        VBox loginForm = new VBox(10);
        loginForm.setPadding(new Insets(20));
        loginForm.setAlignment(Pos.CENTER);

        // Username input
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        // Password input
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Login button
        Button loginButton = styleButton(new Button("Login"));
        loginButton.setMinWidth(150);
        loginButton.setOnAction(e -> app.loginUser(usernameField.getText(), passwordField.getText(), primaryStage));

        // Switch to sign-up form
        Button switchToSignUp = styleButton(new Button("Sign Up"));
        switchToSignUp.setMinWidth(150);
        switchToSignUp.setOnAction(e -> switchToSignUp(primaryStage, app));

        // Adding all components to the login form VBox
        loginForm.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, switchToSignUp);

        return loginForm;
    }

    private static void switchToSignUp(Stage primaryStage, SocialMediaApp app) {
        VBox signUpForm = SignUpFormBuilder.build(primaryStage, app);
        Scene signUpScene = new Scene(signUpForm, 400, 300);
        primaryStage.setScene(signUpScene);
    }

    private static Button styleButton(Button button) {
        button.setStyle("-fx-background-color: #78909C; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;");
        button.setPadding(new Insets(10, 20, 10, 20));
        return button;
    }
}
