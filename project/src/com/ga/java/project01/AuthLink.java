package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class AuthLink {
    static String fileName = "auth_links.csv";

    String authGroupId;
    String command;

    public static void main(String[] args) {
        createAuthLink(new AuthLink("customer", "deposit"));
        createAuthLink(new AuthLink("customer", "withdraw"));
        createAuthLink(new AuthLink("customer", "transfer"));
        createAuthLink(new AuthLink("customer", "set-password"));
        createAuthLink(new AuthLink("customer", "display-history"));
        createAuthLink(new AuthLink("customer", "display-history-past-n"));
    }

    public AuthLink(String authGroupId, String command){
        this.authGroupId = authGroupId;
        this.command = command;
    }

    protected static boolean createAuthLink(AuthLink link){
        if(getAuthLink(link.authGroupId, link.command) != null){
            System.out.println("AuthLink already exists");
            return false;
        }
        List<AuthLink> list = new ArrayList<>();
        list.add(link);
        CSVHandler.appendToCSVFile(fileName, toCSVData(list));
        return true;
    }

    protected boolean delete(){
        List<AuthLink> links = getAllAuthLinks();
        boolean removed = links.removeIf(l -> l.authGroupId.equalsIgnoreCase(this.authGroupId)
                && l.command.equalsIgnoreCase(this.command));
        if(removed){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(links));
        }
        return removed;
    }

    protected static AuthLink getAuthLink(String groupId, String command){
        List<AuthLink> links = getAllAuthLinks();
        for(AuthLink l : links){
            if(l.authGroupId.equalsIgnoreCase(groupId) && l.command.equalsIgnoreCase(command)) return l;
        }
        return null;
    }

    protected static List<AuthLink> getAllAuthLinks(){
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<AuthLink> links = new ArrayList<>();
        if(data != null){
            for(List<String> row : data){
                links.add(new AuthLink(row.get(0), row.get(1)));
            }
        }
        return links;
    }

    protected static List<List<String>> toCSVData(List<AuthLink> links){
        List<List<String>> data = new ArrayList<>();
        for(AuthLink l : links){
            List<String> row = new ArrayList<>();
            row.add(l.authGroupId);
            row.add(l.command);
            data.add(row);
        }
        return data;
    }
}
