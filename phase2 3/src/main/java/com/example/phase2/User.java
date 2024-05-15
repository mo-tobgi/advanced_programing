package com.example.phase2;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private String bio;
    private String profilePictureUrl;
    private String password;
    private List<User> friends;
    private List<Post> posts;
    private List<Message> messages;
    private int followersCount;

    public User(String username, String bio, String profilePictureUrl, String password) {
        validate(username, password);
        this.username = username;
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
        this.password = password;
        this.friends = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.followersCount = 0;
    }
    private void validate(String username, String Password) {
        if (username.length() < 4) {
            throw new IllegalArgumentException("username must be more than 4 characters");
        }
        if (username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot contain spaces.");
        }
        if (Password.length() < 8) {
            throw new IllegalArgumentException("password must be more than 8 characters ");
        }
        if (Password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces.");
        }
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Post> getPosts() { return posts; }
    public String getBio() {return bio;}
    public String getProfilePictureUrl() {return profilePictureUrl;}
    public List<User> getFriends() {return friends;}
    public void setBio(String bio) {this.bio = bio;}
    public void setProfilePictureUrl(String profilePictureUrl) {this.profilePictureUrl = profilePictureUrl;}

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public void addFriend(User friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
            Database.update(SocialMediaApp.getNetworking().getUsers());
        }
        else {
            throw new IllegalArgumentException("you are already friends :)");
        }

    }

    public Post createPost(String content) {
        Post newPost = new Post(this, content);
        posts.add(newPost);
        Database.update(SocialMediaApp.getNetworking().getUsers());
        return newPost;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", friendsCount=" + friends.size() +
                ", postsCount=" + posts.size() +
                '}';
    }
}
