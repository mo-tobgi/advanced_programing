package com.example.phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.phase2.Home.updateFriendsPostsView;
import static com.example.phase2.SocialMediaApp.postsLayout;

public class FriendProfileView {

    public static void showUserProfile(Stage primaryStage, User friend, SocialMediaApp app) {
        VBox profileLayout = new VBox(10);
        profileLayout.setPadding(new Insets(20));
        profileLayout.setAlignment(Pos.CENTER);

        ImageView profileImageView = createImageView(friend);

        Label usernameLabel = new Label("Username: " + friend.getUsername());
        Label bioLabel = new Label("Bio: " + friend.getBio());
        Label followerCountLabel = new Label("Followers: " + friend.getFollowersCount());
        followerCountLabel.setOnMouseClicked(e -> FollowersView.showFollowers(primaryStage, friend, app));

        Label followingCountLabel = new Label("Following: " + friend.getFriends().size());
        followingCountLabel.setOnMouseClicked(e -> FollowersView.showFollowing(primaryStage, friend, app));

        Button friendToggleButton = new Button();
        updateFriendshipButton(friendToggleButton, app.getCurrentUser(), friend);
        friendToggleButton.setOnAction(e -> {
            toggleFriendship(app.getCurrentUser(), friend);
            updateFriendshipButton(friendToggleButton, app.getCurrentUser(), friend);
            followerCountLabel.setText("Followers: " + friend.getFollowersCount());
        });

        Button chatButton = new Button("Chat");
        chatButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        chatButton.setOnAction(e -> ChatView.showChat(primaryStage, app.getCurrentUser(), friend, app));

        Button returnButton = new Button("Return");
        returnButton.setStyle("-fx-font-size: 14px; -fx-background-color: #78909C; -fx-text-fill: white;");
        returnButton.setOnAction(e -> {
            updateFriendsPostsView(postsLayout, app.getCurrentUser(), app, primaryStage);
            primaryStage.setScene(app.postsScene);
        });

        ScrollPane scrollPane = createPostsScrollPane(friend);

        profileLayout.getChildren().addAll(
                profileImageView,
                usernameLabel,
                bioLabel,
                followerCountLabel,
                followingCountLabel,
                friendToggleButton,
                chatButton,
                scrollPane,
                returnButton
        );

        Scene profileScene = new Scene(profileLayout, 300, 500); // Adjusted height for better viewing
        primaryStage.setScene(profileScene);
    }

    private static ImageView createImageView(User friend) {
        ImageView profileImageView = new ImageView();
        String imageUrl = friend.getProfilePictureUrl();
        try (InputStream stream = new FileInputStream(imageUrl)) {
            profileImageView.setImage(new Image(stream));
        } catch (IOException e) {
            System.out.println("Fallback to default image due to: " + e.getMessage());
            profileImageView.setImage(new Image("file:resources/x.png")); // Using a default image path
        }
        profileImageView.setFitHeight(100);
        profileImageView.setFitWidth(100);
        profileImageView.setPreserveRatio(true);
        return profileImageView;
    }

    private static ScrollPane createPostsScrollPane(User friend) {
        ScrollPane scrollPane = new ScrollPane();
        VBox postsLayout = new VBox(5);
        postsLayout.setPadding(new Insets(10));
        scrollPane.setContent(postsLayout);
        scrollPane.setFitToWidth(true);
        List<Post> reversedPosts = new ArrayList<>(friend.getPosts());
        Collections.reverse(reversedPosts);
        for (Post post : reversedPosts) {
            VBox postBox = new VBox(5);
            postBox.getChildren().add(new Label("Content: " + post.getContent()));
            postBox.getChildren().add(new Label("Likes: " + post.getLikeCount()));
            VBox commentsBox = new VBox(2);
            commentsBox.getChildren().add(new Label("Comments:"));
            for (Comment comment : post.getComments()) {
                commentsBox.getChildren().add(new Label(comment.getCommenter().getUsername() + ": " + comment.getText()));
            }
            postBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10;");
            postBox.getChildren().add(commentsBox);
            postsLayout.getChildren().add(postBox);
        }
        return scrollPane;
    }

    private static void updateFriendshipButton(Button friendToggleButton, User currentUser, User friend) {
        if (currentUser.getFriends().contains(friend)) {
            friendToggleButton.setText("Remove Friend");
        } else {
            friendToggleButton.setText("Add Friend");
        }
    }

    private static void toggleFriendship(User currentUser, User friend) {
        if (currentUser.getFriends().contains(friend)) {
            currentUser.getFriends().remove(friend);
            friend.setFollowersCount(friend.getFollowersCount() - 1);
            Database.update(SocialMediaApp.getNetworking().getUsers());
        } else {
            currentUser.addFriend(friend);
            friend.setFollowersCount(friend.getFollowersCount() + 1);
            Database.update(SocialMediaApp.getNetworking().getUsers());
        }
    }
}
