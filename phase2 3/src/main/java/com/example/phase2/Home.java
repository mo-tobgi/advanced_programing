package com.example.phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Comparator;
import java.util.List;
import javafx.scene.Scene;


public class Home {

    public static VBox build(Stage primaryStage, SocialMediaApp app, User currentUser, VBox postsLayout) {
        postsLayout.setPadding(new Insets(10));
        postsLayout.getChildren().clear();

        TextField postContentField = new TextField();
        postContentField.setPromptText("What's on your mind?");
        postContentField.setMinHeight(40); // Enhancing usability with a larger text field

        Button createPostButton = styleButton(new Button("Create Post"));
        createPostButton.setOnAction(e -> handleCreatePost(postContentField, postsLayout, currentUser, app, primaryStage));

        TextField searchUsernameField = new TextField();
        searchUsernameField.setPromptText("Enter username to find friends");
        searchUsernameField.setMinHeight(40); // Consistent design with post field

        Button searchButton = styleButton(new Button("Search"));
        searchButton.setOnAction(e -> search(searchUsernameField, postsLayout, currentUser, app, primaryStage));

        VBox postAndSearchBox = new VBox(15, postContentField, createPostButton, searchUsernameField, searchButton);
        postAndSearchBox.setAlignment(Pos.CENTER);
        postAndSearchBox.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(postsLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox container = new VBox(postAndSearchBox, scrollPane);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.TOP_CENTER);

        HBox navigationButtons = createNavigationButtons(primaryStage, currentUser, app);
        container.getChildren().add(navigationButtons);

        updateFriendsPostsView(postsLayout, currentUser, app, primaryStage);

        return container;
    }

    private static HBox createNavigationButtons(Stage primaryStage, User currentUser, SocialMediaApp app) {
        Button exploreButton = styleButton(new Button("Explore"));
        exploreButton.setOnAction(e -> {
            VBox exploreView = ExploreView.build(primaryStage, app, currentUser);
            primaryStage.getScene().setRoot(exploreView);
        });

        Button profileButton = styleButton(new Button("Profile"));
        profileButton.setOnAction(e -> UserProfileView.showUserProfile(primaryStage, currentUser, app));

        Button logoutButton = styleButton(new Button("Logout"));
        logoutButton.setOnAction(e -> app.logout(primaryStage));

        HBox buttonBox = new HBox(10, exploreButton, profileButton, logoutButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        return buttonBox;
    }

    private static Button styleButton(Button button) {
        button.setStyle("-fx-background-color: #78909C; -fx-text-fill: white;");
        button.setMinHeight(40);
        button.setMinWidth(100);
        return button;
    }



    private static void handleCreatePost(TextField postContentField, VBox postsLayout, User currentUser, SocialMediaApp app, Stage primaryStage) {
        String content = postContentField.getText();
        if (!content.isEmpty()) {
            currentUser.createPost(content);
            postContentField.clear();
            updateFriendsPostsView(postsLayout, currentUser, app, primaryStage);
        }
    }

    private static void search(TextField searchUsernameField, VBox postsLayout, User currentUser, SocialMediaApp app, Stage primaryStage) {
        String friendUsername = searchUsernameField.getText();
        if (!friendUsername.isEmpty() && ! friendUsername.equals(currentUser.getUsername())) {
            try {
                User friend = SocialMediaApp.getNetworking().getUser(friendUsername);
                FriendProfileView.showUserProfile(primaryStage, friend, app);

            } catch (IllegalArgumentException ex) {
                app.showAlert(Alert.AlertType.ERROR, "search Error", ex.getMessage());
            }
        } else {
            app.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid username or cannot search yourself.");
        }
    }


    public static void updateFriendsPostsView(VBox postsLayout, User currentUser, SocialMediaApp app, Stage primaryStage) {
        postsLayout.getChildren().clear();  // Clear previous posts

        currentUser.getFriends().stream()  // Stream of friends
                .flatMap(friend -> friend.getPosts().stream())  // Stream of all posts from all friends
                .sorted(Comparator.comparing(Post::getTime).reversed())  // Sort by time, newest first
                .forEach(post -> addPostUI(postsLayout, post.getAuthor(), post, currentUser, app, primaryStage));  // Add posts to UI
    }

    static void addPostUI(VBox postsLayout, User friend, Post post, User currentUser, SocialMediaApp app, Stage primaryStage) {
        Hyperlink postLink = new Hyperlink(friend.getUsername() + ": " + post.getContent());
        postLink.setOnAction(event -> {
            // Assuming FriendProfileView.showUserProfile takes the primary stage and a user object
            FriendProfileView.showUserProfile(primaryStage, friend, app);
        });
        TextField commentField = new TextField();
        commentField.setPromptText("Enter a comment");

        Button commentButton = new Button("Comment");
        commentButton.setOnAction(e -> {
            String commentContent = commentField.getText();
            post.addComment(currentUser, commentContent);
            commentField.clear();
            updateFriendsPostsView(postsLayout, currentUser, app, primaryStage);
        });

        Button likeButton = new Button();
        post.isLiked(currentUser, likeButton);
        likeButton.setOnAction(e -> {
            post.toggleLike(currentUser, likeButton);
            updateFriendsPostsView(postsLayout, currentUser, app, primaryStage);  // Refresh to show like status change
        });

        VBox commentsBox = new VBox(5);
        List<Comment> sortedComments = post.getComments().stream()
                .sorted(Comparator.comparing(Comment::getTime).reversed())
                .toList();

        for (Comment comment : sortedComments) {
            Label commentLabel = new Label(comment.getCommenter().getUsername() + ": " + comment.getText());
            commentsBox.getChildren().add(commentLabel);
        }

        HBox commentBox = new HBox(5, commentField, commentButton);
        commentBox.setAlignment(Pos.CENTER_LEFT);

        HBox postBox = new HBox(10, postLink, likeButton);
        postBox.setAlignment(Pos.CENTER_LEFT);

        VBox postWithComments = new VBox(10, postBox, commentsBox, commentBox);
        postWithComments.setPadding(new Insets(10));
        postWithComments.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 10;");

        postsLayout.getChildren().add(postWithComments);
    }
    public static Scene getMainScene(Stage primaryStage, SocialMediaApp app, User currentUser) {
        VBox postsLayout = new VBox(10);
        return new Scene(build(primaryStage, app, currentUser, postsLayout), 600, 400);
    }

}