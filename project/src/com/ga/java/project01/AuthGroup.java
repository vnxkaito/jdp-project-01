package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class AuthGroup {
    static String fileName = "auth_groups.csv";

    public static void main(String[] args) {
        boolean ret;
        ret = createAuthGroup(new AuthGroup("customer", "Customer"));
        ret = createAuthGroup(new AuthGroup("banker", "Banker"));
        ret = createAuthGroup(new AuthGroup("admin", "Admin"));
    }

    String authGroupId;
    String authGroupName;

    public AuthGroup(String authGroupId, String authGroupName){
        this.authGroupId = authGroupId;
        this.authGroupName = authGroupName;
    }

    protected static AuthGroup getAuthGroup(String id){
        List<AuthGroup> groups = getAllAuthGroups();
        for(AuthGroup g : groups){
            if(g.authGroupId.equalsIgnoreCase(id)) return g;
        }
        return null;
    }

    protected static boolean createAuthGroup(AuthGroup g){
        if(getAuthGroup(g.authGroupId) != null){
            System.out.println("AuthGroup already exists");
            return false;
        }
        List<AuthGroup> list = new ArrayList<>();
        list.add(g);
        CSVHandler.appendToCSVFile(fileName, toCSVData(list));
        return true;
    }

    protected boolean update(){
        List<AuthGroup> groups = getAllAuthGroups();
        boolean found = false;
        for(int i = 0; i < groups.size(); i++){
            if(groups.get(i).authGroupId.equalsIgnoreCase(this.authGroupId)){
                groups.set(i, this);
                found = true;
                break;
            }
        }
        if(found){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(groups));
        }
        return found;
    }

    protected boolean delete(){
        List<AuthGroup> groups = getAllAuthGroups();
        boolean removed = groups.removeIf(g -> g.authGroupId.equalsIgnoreCase(this.authGroupId));
        if(removed){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(groups));
        }
        return removed;
    }

    protected static List<AuthGroup> getAllAuthGroups(){
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<AuthGroup> groups = new ArrayList<>();
        if(data != null){
            for(List<String> row : data){
                groups.add(new AuthGroup(row.get(0), row.get(1)));
            }
        }
        return groups;
    }

    protected static List<List<String>> toCSVData(List<AuthGroup> groups){
        List<List<String>> data = new ArrayList<>();
        for(AuthGroup g : groups){
            List<String> row = new ArrayList<>();
            row.add(g.authGroupId);
            row.add(g.authGroupName);
            data.add(row);
        }
        return data;
    }
}
