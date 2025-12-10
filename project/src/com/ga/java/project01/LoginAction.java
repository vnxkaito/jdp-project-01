package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class LoginAction implements Action {
    public static ArrayList<String> failedLoginAttempts = new ArrayList<>();

    @Override
    public boolean execute(String[] args) {
        if(args.length < 2){
            System.out.println("Usage: login <userId> <password>");
            return false;
        }

        String userId = args[0];
        String password = args[1];

        User user = User.getUser(userId);
        if(user == null){
            System.out.println("User not found.");
            return false;
        }

        if(failedLoginAttempts.stream().filter(a->a.equalsIgnoreCase(userId)).count() >= 3){
            System.out.println("User is locked due to many failed login attempts");
            return false;
        }

        if(!User.checkPassword(user, password)){
            System.out.println("Incorrect password.");
            failedLoginAttempts.add(user.userId);
            return false;
        }

        // Get user's auth groups
        List<String> authGroups = new ArrayList<>();
        authGroups = AuthAssign.getUserAuthGroups(userId).stream().map(a->a.authGroupId).toList();

        // Create session
        Session.create(userId, authGroups);
        System.out.println("Login successful. Auth groups: " + authGroups);
        return true;
    }

    @Override
    public String getName() {
        return "login";
    }
}
