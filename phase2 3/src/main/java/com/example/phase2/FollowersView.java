package com.example.phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class FollowersView {

    public static void showFollowers(Stage primaryStage, User currentUser, SocialMediaApp app) {
        List<User> followers = SocialMediaApp.getNetworking().getUsers().values().stream()
                .filter(user -> user.getFriends().contains(currentUser))
                .collect(Collectors.toList());

        showUserList(primaryStage, currentUser, followers, "Followers", app);
    }

    public static void showFollowing(Stage primaryStage, User currentUser, SocialMediaApp app) {
        List<User> following = currentUser.getFriends();

        showUserList(primaryStage, currentUser, following, "Following", app);
    }

    private static void showUserList(Stage primaryStage, User currentUser, List<User> users, String title, SocialMediaApp app) {
        BorderPane root = new BorderPane();
        VBox followersLayout = new VBox(10);
        followersLayout.setPadding(new Insets(20));
        followersLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ScrollPane scrollPane = new ScrollPane();
        VBox usersLayout = new VBox(10);
        usersLayout.setPadding(new Insets(10));
        scrollPane.setContent(usersLayout);
        scrollPane.setFitToWidth(true);

        users.forEach(user -> {
            Label userLabel = createUserLabel(user);
            userLabel.setOnMouseClicked(e -> {
                if (!user.equals(app.getCurrentUser())) {


                    FriendProfileView.showUserProfile(primaryStage, user, app);
                }
            });
            usersLayout.getChildren().add(userLabel);
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> UserProfileView.showUserProfile(primaryStage, currentUser, app));
        backButton.setStyle("-fx-background-color: #78909C; -fx-text-fill: white; -fx-font-weight: bold;");

        followersLayout.getChildren().addAll(titleLabel, scrollPane, backButton);
        root.setCenter(followersLayout);

        Scene followersScene = new Scene(root, 500, 600);
        primaryStage.setScene(followersScene);
    }

    private static Label createUserLabel(User user) {
        Label userLabel = new Label(user.getUsername());
        userLabel.setStyle("-fx-font-size: 14px;");
        return userLabel;
    }
}
