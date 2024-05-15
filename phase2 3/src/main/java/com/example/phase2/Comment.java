package com.example.phase2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class Comment implements Serializable, Comparable<Comment> {
    private final User commenter;
    private String text;
    private final LocalDateTime time;

    public Comment(User commenter, String text) {
        validate(commenter, text);
        this.commenter = commenter;
        this.text = text;
        this.time = LocalDateTime.now();
    }

    private void validate(User commenter, String text) {
        if (commenter == null) {
            throw new IllegalArgumentException("Commenter must not be null");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text must not be null or empty");
        }
    }

    // Getters
    public User getCommenter() {
        return commenter;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = time.format(formatter);
        return "Comment{" +
                "commenter=" + commenter +
                ", text='" + text + '\'' +
                ", time=" + formattedTime +
                '}';
    }

    public int compareTo(Comment other) {
        return this.time.compareTo(other.time);
    }
}


