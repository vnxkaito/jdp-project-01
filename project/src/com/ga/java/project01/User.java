package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class User {
    static String fileName = "users";

    String userId;
    String password;
    String personId;
    String accountId;

    public static void main(String[] args) {
//        createUser(new User("customer1", "customer1", "Customer 1", "acc001c"));
//        createUser(new User("customer2", "customer2", "Customer 2", "acc002s"));
//        createUser(new User("customer3", "customer3", "Customer 3", "acc003c"));
        createUser(new User("banker", "banker", "Banker", ""));
    }

    private User(String userId, String password, String personId, String accountId){
        this.userId = userId;
        this.password = password;
        this.personId = personId;
        this.accountId = accountId;
    }

    protected static User getUser(String userId){
        List<User> users = getAllUsers();
        for(User user : users){
            if(user.userId.equalsIgnoreCase(userId)){
                return new User(user.userId, user.password, user.personId, user.accountId);
            }
        }
        return null;
    }



    protected static boolean createUser(User user){
        if(getUser(user.userId) != null){
            System.out.println("This user id already exists");
            return false;
        }
        user.password = PasswordHandler.encrypt(user.password);
        List<User> users = new ArrayList<>();
        users.add(user);
        CSVHandler.appendToCSVFile(user.fileName, user.toCSVData(users));
        return true;
    }

    protected boolean update(){
        List<User> users = getAllUsers();
        boolean found = false;

        for(int i = 0; i < users.size(); i++){
            if(users.get(i).userId.equalsIgnoreCase(this.userId)){
                this.password = PasswordHandler.encrypt(this.password);
                users.set(i, this);
                found = true;
                break;
            }
        }
        if (found){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(users));
        }
        return found;
    }

    protected boolean delete(){
        List<User> users = getAllUsers();
        boolean found = false;

        for(int i = 0; i < users.size(); i++){
            if(users.get(i).userId.equalsIgnoreCase(this.userId)){
                users.remove(i);
                found = true;
                break;
            }
        }
        if (found){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(users));
        }
        return found;
    }

    protected static List<User> getAllUsers(){
        List<List<String>> userData = CSVHandler.readCSV(fileName);
        List<User> users = new ArrayList<>();

        for (List<String> row : userData){
            User user = new User(
                    // to-do change this hard coded order
                    row.get(0), // userId
                    row.get(1), // password
                    row.get(2), // personId
                    row.get(3) // accountId
            );
            users.add(user);
        }
        return users;
    }

    protected List<List<String>> toCSVData(List<User> users){
        List<List<String>> data = new ArrayList<>();

        for(User user : users){
            List<String> row = new ArrayList<>();
            row.add(user.userId);
            row.add(user.password);
            row.add(user.personId);
            row.add(user.accountId);

            data.add(row);
        }

        return data;

    }

    public static boolean checkPassword(User user, String inputPassword){
        String encryptedInput = PasswordHandler.encrypt(inputPassword);
        return user.password.equals(encryptedInput);
    }

    protected static User getUserByAccountId(String accountId){
        List<User> users = getAllUsers();
        for(User u : users){
            if(u.accountId.equalsIgnoreCase(accountId)) return u;
        }
        return null;
    }


}
