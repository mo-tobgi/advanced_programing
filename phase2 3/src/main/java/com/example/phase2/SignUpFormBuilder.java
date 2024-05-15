package com.example.phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpFormBuilder {
    static VBox build(Stage primaryStage, SocialMediaApp app) {
        VBox signUpForm = new VBox(10);
        signUpForm.setPadding(new Insets(20));
        signUpForm.setAlignment(Pos.CENTER);

        // Username input
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Choose a username");

        // Password input
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Choose a password");

        // Bio input
        Label bioLabel = new Label("Bio:");
        TextField bioField = new TextField();
        bioField.setPromptText("Tell us about yourself");

        // Profile picture URL input
        Label profilePicLabel = new Label("Profile Picture URL:");
        TextField profilePicUrlField = new TextField();
        profilePicUrlField.setPromptText("Link to your profile picture");

        // Sign Up button
        Button signUpButton = styleButton(new Button("Sign Up"));
        signUpButton.setMinWidth(150);
        signUpButton.setOnAction(e -> app.registerUser(
                usernameField.getText(), bioField.getText(), profilePicUrlField.getText(),
                passwordField.getText(), primaryStage));

        // Switch to Log in form
        Button switchToLogin = styleButton(new Button("Login"));
        switchToLogin.setMinWidth(150);
        switchToLogin.setOnAction(e -> app.setupLoginScene(primaryStage));

        // Adding all components to the sign-up form VBox
        signUpForm.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField,
                bioLabel, bioField, profilePicLabel, profilePicUrlField, signUpButton, switchToLogin);

        return signUpForm;
    }

    private static Button styleButton(Button button) {
        button.setStyle("-fx-background-color: #78909C; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;");
        button.setPadding(new Insets(10, 20, 10, 20));
        return button;
    }
}
