package com.ga.java.project01;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OverdraftRecord extends Overdraft {

    static String fileName = "overdrafts.csv";

    public OverdraftRecord(String accountId, double feeAmount){
        super(accountId, feeAmount, LocalDateTime.now());
    }

    public OverdraftRecord(String accountId, double feeAmount, LocalDateTime timestamp){
        super(accountId, feeAmount, timestamp);
    }

    protected static boolean createOverdraft(OverdraftRecord o){
        List<OverdraftRecord> list = new ArrayList<>();
        list.add(o);
        CSVHandler.appendToCSVFile(fileName, toCSVData(list));
        return true;
    }

    @Override
    protected boolean create() {
        return createOverdraft(this);
    }

    @Override
    protected boolean delete() {
        List<OverdraftRecord> list = getAllOverdrafts();
        boolean removed = list.removeIf(o ->
                o.accountId.equalsIgnoreCase(this.accountId)
                        && o.timestamp.equals(this.timestamp)
        );

        if (removed) {
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(list));
        }

        return removed;
    }

    @Override
    protected boolean update() {
        List<OverdraftRecord> list = getAllOverdrafts();
        boolean found = false;

        for (int i = 0; i < list.size(); i++) {
            OverdraftRecord o = list.get(i);
            if (o.accountId.equalsIgnoreCase(this.accountId)
                    && o.timestamp.equals(this.timestamp))
            {
                list.set(i, this);
                found = true;
                break;
            }
        }

        if (found) {
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(list));
        }

        return found;
    }

    protected static List<OverdraftRecord> getAllOverdrafts(){
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<OverdraftRecord> list = new ArrayList<>();

        if (data != null){
            for (List<String> row : data){
                String accountId = row.get(0);
                double fee = Double.parseDouble(row.get(1));
                LocalDateTime ts = LocalDateTime.parse(row.get(2));
                list.add(new OverdraftRecord(accountId, fee, ts));
            }
        }

        return list;
    }

    protected static List<List<String>> toCSVData(List<OverdraftRecord> list){
        List<List<String>> data = new ArrayList<>();
        for (OverdraftRecord o : list){
            List<String> row = new ArrayList<>();
            row.add(o.accountId);
            row.add(String.valueOf(o.feeAmount));
            row.add(o.timestamp.toString());
            data.add(row);
        }
        return data;
    }

    public static double getTotalFees(String accountId){
        return getAllOverdrafts().stream()
                .filter(o -> o.accountId.equalsIgnoreCase(accountId))
                .mapToDouble(o -> o.feeAmount)
                .sum();
    }
}
