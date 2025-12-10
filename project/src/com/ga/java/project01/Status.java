package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;

public class Status {
    static String fileName = "status.csv";

    String statusId;
    String statusClass;
    String statusName;
    String statusDescription;

    public Status(String statusId, String statusClass, String statusName, String statusDescription){
        this.statusId = statusId;
        this.statusClass = statusClass;
        this.statusName = statusName;
        this.statusDescription = statusDescription;
    }

    protected static Status getStatus(String id){
        List<Status> statuses = getAllStatus();
        for(Status s : statuses){
            if(s.statusId.equalsIgnoreCase(id)) return s;
        }
        return null;
    }

    protected static boolean createStatus(Status s){
        if(getStatus(s.statusId) != null){
            System.out.println("Status already exists");
            return false;
        }
        List<Status> list = new ArrayList<>();
        list.add(s);
        CSVHandler.appendToCSVFile(fileName, toCSVData(list));
        return true;
    }

    protected boolean update(){
        List<Status> statuses = getAllStatus();
        boolean found = false;
        for(int i = 0; i < statuses.size(); i++){
            if(statuses.get(i).statusId.equalsIgnoreCase(this.statusId)){
                statuses.set(i, this);
                found = true;
                break;
            }
        }
        if(found){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(statuses));
        }
        return found;
    }

    protected boolean delete(){
        List<Status> statuses = getAllStatus();
        boolean removed = statuses.removeIf(s -> s.statusId.equalsIgnoreCase(this.statusId));
        if(removed){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(statuses));
        }
        return removed;
    }

    protected static List<Status> getAllStatus(){
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<Status> statuses = new ArrayList<>();
        if(data != null){
            for(List<String> row : data){
                statuses.add(new Status(row.get(0), row.get(1), row.get(2), row.get(3)));
            }
        }
        return statuses;
    }

    protected static List<List<String>> toCSVData(List<Status> statuses){
        List<List<String>> data = new ArrayList<>();
        for(Status s : statuses){
            List<String> row = new ArrayList<>();
            row.add(s.statusId);
            row.add(s.statusClass);
            row.add(s.statusName);
            row.add(s.statusDescription);
            data.add(row);
        }
        return data;
    }
}
