package com.example.phase2;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChatView {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void showChat(Stage primaryStage, User currentUser, User otherUser, SocialMediaApp app) {
        VBox chatLayout = new VBox(10);
        chatLayout.setPadding(new Insets(20));
        chatLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Chat with " + otherUser.getUsername());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ScrollPane scrollPane = new ScrollPane();
        VBox messagesBox = new VBox(10);
        messagesBox.setPadding(new Insets(10));
        scrollPane.setContent(messagesBox);
        scrollPane.setFitToWidth(true);

        List<Message> chatMessages = getChatMessages(currentUser, otherUser);
        for (Message message : chatMessages) {
            HBox messageBox = new HBox(10);
            VBox messageContent = new VBox(5);
            messageContent.getChildren().add(new Label(message.getText()));
            messageContent.getChildren().add(new Label(message.getTimestamp().format(formatter)));
            messageContent.setStyle("-fx-background-color: #F1F1F1; -fx-padding: 10; -fx-border-radius: 10;");

            if (message.getSender().equals(currentUser)) {
                messageBox.setAlignment(Pos.CENTER_RIGHT);
                messageContent.setStyle("-fx-background-color: #DCF8C6; -fx-padding: 10; -fx-border-radius: 10;");
            } else {
                messageBox.setAlignment(Pos.CENTER_LEFT);
                messageContent.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10; -fx-border-radius: 10; -fx-border-color: #DDDDDD;");
            }

            messageBox.getChildren().add(messageContent);
            messagesBox.getChildren().add(messageBox);
        }

        TextArea messageTextArea = new TextArea();
        messageTextArea.setPromptText("Type your message here...");
        messageTextArea.setWrapText(true);
        messageTextArea.setPrefHeight(60);

        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        sendButton.setOnAction(e -> {
            String messageText = messageTextArea.getText();
            if (!messageText.trim().isEmpty()) {
                sendMessage(currentUser, otherUser, messageText);
                Database.update(SocialMediaApp.getNetworking().getUsers());
                messageTextArea.clear();
                showChat(primaryStage, currentUser, otherUser, app);
            }
        });

        HBox inputBox = new HBox(10, messageTextArea, sendButton);
        inputBox.setPadding(new Insets(10));
        inputBox.setAlignment(Pos.CENTER);

        Button returnButton = new Button("Return");
        returnButton.setStyle("-fx-font-size: 14px; -fx-background-color: #78909C; -fx-text-fill: white;");
        returnButton.setOnAction(e -> FriendProfileView.showUserProfile(primaryStage, otherUser, app));

        chatLayout.getChildren().addAll(titleLabel, scrollPane, inputBox, returnButton);

        Scene chatScene = new Scene(chatLayout, 400, 600);
        primaryStage.setUserData(primaryStage.getScene());
        primaryStage.setScene(chatScene);
    }

    private static List<Message> getChatMessages(User currentUser, User otherUser) {
        List<Message> chatMessages = new ArrayList<>();
        for (Message message : currentUser.getMessages()) {
            if (message.getSender().equals(otherUser)  ) {
                chatMessages.add(message);
            }
        }

        for (Message message : otherUser.getMessages()) {
            if ( message.getSender().equals(currentUser)) {
                chatMessages.add(message);
            }
        }

        chatMessages.sort(Comparator.comparing(Message::getTimestamp));
        return chatMessages;
    }

    private static void sendMessage(User sender, User receiver, String text) {
        Message message = new Message(sender, text, receiver);
        receiver.getMessages().add(message);
    }
}

