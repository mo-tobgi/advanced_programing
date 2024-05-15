package com.example.phase2;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private User sender;
    private User receiver;
    private String text;
    private final LocalDateTime timestamp;

    public Message(User sender, String text, User receiver) {
        this.sender = sender;
        this.text = text;
        this.receiver = receiver;
        this.timestamp = LocalDateTime.now();
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public User getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}

