package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class Authorization {
    static String fileName = "authorizations.csv";

    String userId;
    String authGroupId;

    public Authorization(String userId, String authGroupId){
        this.userId = userId;
        this.authGroupId = authGroupId;
    }

    public static List<Authorization> getAllAuthorizations() {
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<Authorization> auths = new ArrayList<>();
        if(data != null) {
            for(List<String> row : data){
                auths.add(new Authorization(row.get(0), row.get(1)));
            }
        }
        return auths;
    }

    public boolean create() {
        List<Authorization> auths = getAllAuthorizations();
        auths.add(this);
        CSVHandler.appendToCSVFile(fileName, toCSVData(auths));
        return true;
    }

    public boolean delete() {
        List<Authorization> auths = getAllAuthorizations();
        boolean found = false;
        for(int i = 0; i < auths.size(); i++){
            Authorization a = auths.get(i);
            if(a.userId.equalsIgnoreCase(this.userId) &&
                    a.authGroupId.equalsIgnoreCase(this.authGroupId)){
                auths.remove(i);
                found = true;
                break;
            }
        }
        if(found){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(auths));
        }
        return found;
    }

    private List<List<String>> toCSVData(List<Authorization> auths){
        List<List<String>> data = new ArrayList<>();
        for(Authorization a : auths){
            List<String> row = new ArrayList<>();
            row.add(a.userId);
            row.add(a.authGroupId);
            data.add(row);
        }
        return data;
    }

    public static boolean isAuthorized(Session session, String commandName) {
        if(session == null) return false;

        List<String> authGroups = session.getAuthGroups();
        for(String groupId : authGroups){
            AuthLink link = AuthLink.getAuthLink(groupId, commandName);
            if(link != null) return true;
        }
        return false;
    }
}
