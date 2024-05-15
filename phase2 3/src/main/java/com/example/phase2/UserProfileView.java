package com.example.phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserProfileView {

    public static void showUserProfile(Stage primaryStage, User currentUser, SocialMediaApp app) {
        BorderPane root = new BorderPane();
        VBox profileLayout = new VBox(20); // Increased spacing for a cleaner look
        profileLayout.setPadding(new Insets(20));
        profileLayout.setAlignment(Pos.CENTER);

        ImageView profileImageView = new ImageView();
        loadImage(profileImageView, currentUser.getProfilePictureUrl());
        profileImageView.setStyle("-fx-border-radius: 5; -fx-border-width: 2; -fx-border-color: #ccc;"); // Added styling for image

        TextField usernameField = new TextField(currentUser.getUsername());
        usernameField.setEditable(false);
        styleTextField(usernameField);

        TextField bioField = new TextField(currentUser.getBio());
        bioField.setPromptText("Enter your bio");
        styleTextField(bioField);

        TextField profilePicUrlField = new TextField(currentUser.getProfilePictureUrl());
        profilePicUrlField.setPromptText("Profile Picture URL");
        styleTextField(profilePicUrlField);

        // Add labels for follower count and following count
        Label followerCountLabel = new Label("Followers: " + currentUser.getFollowersCount());
        followerCountLabel.setOnMouseClicked(e -> FollowersView.showFollowers(primaryStage, currentUser, app));

        Label followingCountLabel = new Label("Following: " + currentUser.getFriends().size());
        followingCountLabel.setOnMouseClicked(e -> FollowersView.showFollowing(primaryStage, currentUser, app));


        Button saveButton = setupSaveButton(primaryStage, currentUser, bioField, profilePicUrlField, app);
        styleButton(saveButton);

        Button cancelButton = setupCancelButton(primaryStage, app);
        styleButton(cancelButton);

        profileLayout.getChildren().addAll(
                profileImageView,
                new Label("Username"), usernameField,
                new Label("Bio"), bioField,
                new Label("Profile Picture URL"), profilePicUrlField,
                followerCountLabel, // Added follower count label
                followingCountLabel, // Added following count label
                saveButton, cancelButton
        );

        ScrollPane scrollPane = setupPostsPane(currentUser);

        root.setTop(profileLayout);
        root.setCenter(scrollPane);

        Scene profileScene = new Scene(root, 500, 600); // Adjusted size for better layout
        primaryStage.setScene(profileScene);
    }

    private static void loadImage(ImageView profileImageView, String imageUrl) {
        try {
            Image img = new Image(new FileInputStream(imageUrl));
            profileImageView.setImage(img);
        } catch (FileNotFoundException e) {
            System.out.println("Fallback to default image due to: " + e.getMessage());
            profileImageView.setImage(new Image("file:resources/x.png"));
        }
        profileImageView.setFitHeight(100);
        profileImageView.setFitWidth(100);
        profileImageView.setPreserveRatio(true);
    }

    private static ScrollPane setupPostsPane(User currentUser) {
        ScrollPane scrollPane = new ScrollPane();
        VBox postsLayout = new VBox(10);
        postsLayout.setPadding(new Insets(10));
        scrollPane.setContent(postsLayout);
        scrollPane.setFitToWidth(true);

        List<Post> reversedPosts = new ArrayList<>(currentUser.getPosts());
        Collections.reverse(reversedPosts);
        reversedPosts.forEach(post -> postsLayout.getChildren().add(createPostBox(post)));

        return scrollPane;
    }

    private static VBox createPostBox(Post post) {
        VBox postBox = new VBox(10);
        postBox.getChildren().addAll(
                new Label("Content: " + post.getContent()),
                new Label("Likes: " + post.getLikeCount()),
                createCommentsBox(post)
        );
        postBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10;");
        return postBox;
    }

    private static VBox createCommentsBox(Post post) {
        VBox commentsBox = new VBox(5);
        commentsBox.getChildren().add(new Label("Comments:"));
        post.getComments().forEach(comment -> commentsBox.getChildren().add(
                new Label(comment.getCommenter().getUsername() + ": " + comment.getText())
        ));
        return commentsBox;
    }

    private static Button setupSaveButton(Stage primaryStage, User currentUser, TextField bioField, TextField profilePicUrlField, SocialMediaApp app) {
        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> {
            currentUser.setBio(bioField.getText());
            currentUser.setProfilePictureUrl(profilePicUrlField.getText());
            Database.update(SocialMediaApp.getNetworking().getUsers());
            app.showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Your profile was updated successfully!");
            primaryStage.setScene(app.postsScene);
        });
        return saveButton;
    }

    private static Button setupCancelButton(Stage primaryStage, SocialMediaApp app) {
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> primaryStage.setScene(app.postsScene));
        return cancelButton;
    }

    private static void styleTextField(TextField field) {
        field.setStyle("-fx-background-color: white; -fx-padding: 4;");
    }

    private static void styleButton(Button button) {
        button.setStyle("-fx-background-color: #78909C; -fx-text-fill: white; -fx-font-weight: bold;");
    }
}
