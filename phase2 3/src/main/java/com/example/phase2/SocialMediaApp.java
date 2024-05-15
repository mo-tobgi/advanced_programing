package com.example.phase2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Serializable;

public class SocialMediaApp extends Application implements Serializable {
    private static final Networking networking = new Networking();
    private User currentUser;
    private Scene loginScene;
    protected Scene postsScene;
    protected static final VBox postsLayout = new VBox(15);

    public static Networking getNetworking() {
        return networking;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Social Media Platform");
        setupLoginScene(primaryStage);
        primaryStage.show();
    }

    protected void setupLoginScene(Stage primaryStage) {
        VBox loginForm = LoginFormBuilder.build(primaryStage, this);
        loginScene = new Scene(loginForm, 400, 300);
        primaryStage.setScene(loginScene);
    }
    void logout(Stage primaryStage) {
        currentUser = null;
        setupLoginScene(primaryStage);
    }
    void loginUser(String username, String password, Stage primaryStage) {
        try {
            currentUser = networking.loginUser(username, password);
            showFriendsPosts(primaryStage);
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Login Error", ex.getMessage());
        }
    }

    void registerUser(String username, String bio, String profilePicUrl, String password, Stage primaryStage) {
        try {
            boolean registered = networking.registerUser(username, bio, profilePicUrl, password);
            if (registered) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Success", "Registration Successful!");
                setupLoginScene(primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "User already exists.");
            }
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", ex.getMessage());
        }
    }

    private void showFriendsPosts(Stage primaryStage) {
        VBox friendsPostsView = Home.build(primaryStage, this, currentUser, postsLayout);
        postsScene = new Scene(friendsPostsView, 500, 500);
        primaryStage.setScene(postsScene);
    }

    void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
