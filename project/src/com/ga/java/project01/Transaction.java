package com.ga.java.project01;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    static String fileName = "transactions.csv";

    String transactionId;
    String debitedAccount;
    String creditedAccount;
    double amount;
    String status;
    LocalDateTime timestamp;

    public Transaction(String transactionId, String debitedAccount, String creditedAccount,
                       double amount, String status, LocalDateTime timestamp){
        this.transactionId = transactionId;
        this.debitedAccount = debitedAccount;
        this.creditedAccount = creditedAccount;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public static void create(String debited, String credited, double amount, String status){
        String id = "TX" + System.currentTimeMillis(); // simple unique id
        Transaction tx = new Transaction(id, debited, credited, amount, status, LocalDateTime.now());
        List<Transaction> transactions = getAllTransactions();
        transactions.add(tx);
        CSVHandler.appendToCSVFile(fileName, toCSVData(transactions));
    }

    public static List<Transaction> getHistory(String accountId, LocalDateTime start, LocalDateTime end){
        List<Transaction> all = getAllTransactions();
        List<Transaction> filtered = new ArrayList<>();
        for(Transaction tx : all){
            boolean involvesAccount = tx.debitedAccount.equalsIgnoreCase(accountId) ||
                    tx.creditedAccount.equalsIgnoreCase(accountId);
            boolean inRange = !tx.timestamp.isBefore(start) && !tx.timestamp.isAfter(end);
            if(involvesAccount && inRange){
                filtered.add(tx);
            }
        }
        return filtered;
    }

    public static List<Transaction> getAllTransactions(){
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<Transaction> transactions = new ArrayList<>();
        if(data != null){
            for(List<String> row : data){
                transactions.add(new Transaction(
                        row.get(0),
                        row.get(1),
                        row.get(2),
                        Double.parseDouble(row.get(3)),
                        row.get(4),
                        LocalDateTime.parse(row.get(5))
                ));
            }
        }
        return transactions;
    }

    private static List<List<String>> toCSVData(List<Transaction> transactions){
        List<List<String>> data = new ArrayList<>();
        for(Transaction tx : transactions){
            List<String> row = new ArrayList<>();
            row.add(tx.transactionId);
            row.add(tx.debitedAccount);
            row.add(tx.creditedAccount);
            row.add(String.valueOf(tx.amount));
            row.add(tx.status);
            row.add(tx.timestamp.toString());
            data.add(row);
        }
        return data;
    }

    @Override
    public String toString(){
        return transactionId + " | " + debitedAccount + " -> " + creditedAccount +
                " | " + amount + " | " + status + " | " + timestamp;
    }
}
