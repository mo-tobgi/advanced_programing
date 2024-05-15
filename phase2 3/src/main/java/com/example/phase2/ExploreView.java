package com.example.phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.phase2.Home.updateFriendsPostsView;

public class ExploreView {

    public static VBox build(Stage primaryStage, SocialMediaApp app, User currentUser) {
        VBox postsLayout = new VBox(10);
        postsLayout.setPadding(new Insets(10));

        // Collect all posts from all users, sort them by like count in descending order
        List<Post> allPosts = SocialMediaApp.getNetworking().getUsers().values().stream()
                .filter(user -> !user.equals(currentUser)) // Exclude current user
                .flatMap(user -> user.getPosts().stream())
                .sorted(Comparator.comparing(Post::getLikeCount).reversed())
                .toList();

        // Display each post in the UI
        allPosts.forEach(post -> addPostUI(postsLayout, post, app, primaryStage, currentUser));

        // Create and configure the return button
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            // Refresh and show the main home scene
            Scene mainScene = Home.getMainScene(primaryStage, app, app.getCurrentUser());
            primaryStage.setScene(mainScene);
        });

        ScrollPane scrollPane = new ScrollPane(postsLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox container = new VBox(scrollPane);
        container.getChildren().add(returnButton);  // Add the return button to the container
        container.setPadding(new Insets(10));
        container.setSpacing(10);  // Ensure some spacing for better layout

        return container;
    }

    private static void addPostUI(VBox postsLayout, Post post, SocialMediaApp app, Stage primaryStage, User currentUser) {
        User author = post.getAuthor();
        Hyperlink postLink = new Hyperlink(author.getUsername() + ": " + post.getContent());
        postLink.setOnAction(event -> FriendProfileView.showUserProfile(primaryStage, author, app));

        TextField commentField = new TextField();
        commentField.setPromptText("Enter a comment");

        Button commentButton = new Button("Comment");
        commentButton.setOnAction(e -> {
            String commentContent = commentField.getText();
            post.addComment(app.getCurrentUser(), commentContent);
            commentField.clear();
            refreshPosts(postsLayout, app, primaryStage, currentUser);
        });

        Button likeButton = new Button();
        post.isLiked(app.getCurrentUser(), likeButton);
        likeButton.setOnAction(e -> {
            post.toggleLike(app.getCurrentUser(), likeButton);
            refreshPosts(postsLayout, app, primaryStage, currentUser);
        });

        VBox commentsBox = new VBox(5);
        post.getComments().stream()
                .sorted(Comparator.comparing(Comment::getTime).reversed())
                .forEach(comment -> {
                    Label commentLabel = new Label(comment.getCommenter().getUsername() + ": " + comment.getText());
                    commentsBox.getChildren().add(commentLabel);
                });

        HBox commentBox = new HBox(5, commentField, commentButton);
        commentBox.setAlignment(Pos.CENTER_LEFT);

        HBox postBox = new HBox(10, postLink, likeButton);
        postBox.setAlignment(Pos.CENTER_LEFT);

        VBox postWithComments = new VBox(10, postBox, commentsBox, commentBox);
        postWithComments.setPadding(new Insets(10));
        postWithComments.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10;");

        postsLayout.getChildren().add(postWithComments);
    }

    private static void refreshPosts(VBox postsLayout, SocialMediaApp app, Stage primaryStage, User currentUser) {
        postsLayout.getChildren().clear();
        build(primaryStage, app, currentUser).getChildren().stream()
                .filter(node -> node instanceof ScrollPane)
                .map(node -> (ScrollPane) node)
                .findFirst()
                .ifPresent(scrollPane -> postsLayout.getChildren().add(scrollPane.getContent()));
    }
}
