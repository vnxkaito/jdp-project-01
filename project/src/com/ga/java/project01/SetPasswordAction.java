package com.ga.java.project01;

public class SetPasswordAction implements Action {

    @Override
    public boolean execute(String[] args) {
        Session session = Session.get();
        if(session == null){
            System.out.println("Please login first!");
            return false;
        }

        if(args.length < 1){
            System.out.println("Usage: set-password <new-password>");
            return false;
        }

        String newPassword = args[0];

        User user = User.getUser(session.getUserId());
        if(user == null){
            System.out.println("User not found.");
            return false;
        }

        user.password = newPassword;
        if(user.update()){
            System.out.println("Password updated successfully!");
            return true;
        } else {
            System.out.println("Failed to update password.");
            return false;
        }
    }

    @Override
    public String getName() {
        return "set-password";
    }
}