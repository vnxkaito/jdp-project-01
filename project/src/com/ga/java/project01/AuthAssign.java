package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class AuthAssign{
    // for assigning authorization to a user
    // to-do for this and other entities: control creation of multiple unique entries
    protected User user;
    protected AuthGroup authGroup;

    static String fileName = "auth_assign.csv";

    public static void main(String[] args) {
        createAssign("customer1", "customer");
        createAssign("customer2", "customer");
        createAssign("customer3", "customer");
        createAssign("banker", "banker");
    }


    protected AuthAssign(String userId, String authGroupId){
        user = User.getUser(userId);
        authGroup = AuthGroup.getAuthGroup(authGroupId);
    }


    //create
    public static boolean createAssign(String userId, String authGroupId){
        AuthAssign authAssign = new AuthAssign(userId, authGroupId);
        CSVHandler csvHandler = new CSVHandler();
        List<AuthAssign> authAssignList = new ArrayList<>();
        authAssignList.add(authAssign);
        return csvHandler.writeToCSVFile(fileName, toCSVData(authAssignList));
    }

    //delete
    public boolean delete(String userId, String authGroupId){
        return AuthAssign.delAssignment(userId, authGroupId);
    }

    public static boolean delAssignment(String userId, String authGroupId){
        CSVHandler csvHandler = new CSVHandler();
        return csvHandler.writeToCSVFile(fileName, toCSVData(getAllAuthAssign()
                .stream().filter(authAssign -> (authAssign.user.userId != userId)
                && (authAssign.authGroup.authGroupId != authGroupId)).toList()));
    }

    //read
    public static List<AuthGroup> getUserAuthGroups(String userId){
        List<AuthAssign> allAssignments = new ArrayList<>();
//        List<AuthGroup> userAuthGroups = new ArrayList<>();
        allAssignments = getAllAuthAssign();

        List<AuthGroup> userAuthGroups = new ArrayList<>();
        userAuthGroups = allAssignments.stream().filter(a->a.user.userId.equalsIgnoreCase(userId))
                .map(authAssign -> authAssign.authGroup).toList();
        return userAuthGroups;

    }

    public static List<List<String>> toCSVData(List<AuthAssign> authAssignList){
        List<List<String>> csvData = new ArrayList<>();
        for(AuthAssign authAssign: authAssignList){
            List<String> csvLine = new ArrayList<>();
            csvLine.add(authAssign.user.userId);
            csvLine.add(authAssign.authGroup.authGroupId);
            csvData.add(csvLine);
        }
        return csvData;
    }

    public static List<AuthAssign> fromCSVData(List<List<String>> csvData){
        List<AuthAssign> authAssignList = new ArrayList<>();
        for(List<String> csvLine: csvData){
            if(csvLine.size() > 1){
                authAssignList.add(new AuthAssign(csvLine.get(0), csvLine.get(1)));
            }
        }
        return authAssignList;
    }


    //readAll
    protected static List<AuthAssign> getAllAuthAssign(){
        return fromCSVData(CSVHandler.readCSV(fileName));
    }

}
