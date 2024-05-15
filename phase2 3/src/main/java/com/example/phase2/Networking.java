package com.example.phase2;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Networking implements Serializable {
    private Map<String, User> users = new HashMap<>();

    public Networking() {
        try {
            FileInputStream fileInput = new FileInputStream("/Users/mohamdtobgi/spring 2024/advanced programming/phase2/data.txt");

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            users = (HashMap)objectInput.readObject();

            objectInput.close();
            fileInput.close();
        }

        catch (IOException obj1) {
            obj1.printStackTrace();
        }
        catch (ClassNotFoundException obj2) {
            System.out.println("Class not found");
            obj2.printStackTrace();
        }

    }

    public Map<String, User> getUsers() {
            return users;
        }


        public boolean registerUser(String username, String bio, String profilePicUrl, String Password) {
            if (!users.containsKey(username)) {
                users.put(username, new User(username, bio, profilePicUrl, Password));
                Database.update(users);
            return true;
            }
            return false; // User already exists
        }

        public User loginUser(String username, String Password) {
            User user = users.get(username);
            if (user != null && user.getPassword().equals(Password)) {
                return user;
            }

            throw new IllegalArgumentException("wrong username or password"); // Login failed
        }



public User getUser(String username){
            if (!users.containsKey(username)) {
                throw new IllegalArgumentException("no user found");
            }
            return users.get(username);
        }


        public Set<String> showUsers(){
           return users.keySet();
        }

    }

    