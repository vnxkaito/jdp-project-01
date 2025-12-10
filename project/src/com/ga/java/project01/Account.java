package com.ga.java.project01;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Account {
    static String fileName = "accounts.csv";
    int overdraftCount;
    double overdraftFees;
    String accountId;
    double balance;
    String cardTypeId;
    String accountType;

    public static void main(String[] args) {
        createAccount(new Account("acc001c", 300_000, "mastercard_platinum", "checking"));
        createAccount(new Account("acc001s", 300_000, "mastercard_platinum", "savings"));
        createAccount(new Account("acc002c", 300_000, "mastercard_titanium", "checking"));
        createAccount(new Account("acc002s", 300_000, "mastercard_titanium", "savings"));
        createAccount(new Account("acc003c", 300_000, "mastercard", "checking"));
        createAccount(new Account("acc003s", 300_000, "mastercard", "savings"));
    }

    public Account(String accountId, double balance, String cardTypeId, String accountType){
        this.accountId = accountId;
        this.balance = balance;
        this.cardTypeId = cardTypeId;
        this.accountType = accountType;
    }

    protected static Account getAccount(String accountId){
        List<Account> accounts = getAllAccounts();
        for(Account a : accounts){
            if(a.accountId.equalsIgnoreCase(accountId)) return a;
        }
        return null;
    }

    protected static boolean createAccount(Account a){
        if(getAccount(a.accountId) != null){
            System.out.println("Account already exists");
            return false;
        }
        List<Account> list = new ArrayList<>();
        list.add(a);
        CSVHandler.appendToCSVFile(fileName, toCSVData(list));
        return true;
    }

    public static Account getAccountByUserId(String userId) {
        User user = User.getUser(userId);
        if(user != null){
            return getAccount(user.accountId);
        } else {
            return null;
        }

    }

//    protected boolean deposit(double amount){
//        this.balance = this.balance + amount;
//        if(!this.update()){
//            this.balance = this.balance - amount; // reverting the runtime data if the update fails
//        }
//        return this.update();
//    }
//
//    protected boolean withdraw(double amount){
//        if(this.balance >= amount){
//            this.balance = this.balance - amount;
//            if(!this.update()){
//                this.balance = this.balance + amount; // reverting the runtime data if the update fails
//            }
//            return this.update();
//        }else{
//            System.out.println("Insufficient funds");
//            return false;
//        }
//    }

    protected boolean update(){
        List<Account> accounts = getAllAccounts();
        boolean found = false;
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).accountId.equalsIgnoreCase(this.accountId)){
                accounts.set(i, this);
                found = true;
                break;
            }
        }
        if(found){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(accounts));
        }
        return found;
    }

    protected boolean delete(){
        List<Account> accounts = getAllAccounts();
        boolean removed = accounts.removeIf(a -> a.accountId.equalsIgnoreCase(this.accountId));
        if(removed){
            CSVHandler handler = new CSVHandler();
            handler.writeToCSVFile(fileName, toCSVData(accounts));
        }
        return removed;
    }

    protected static List<Account> getAllAccounts(){
        List<List<String>> data = CSVHandler.readCSV(fileName);
        List<Account> accounts = new ArrayList<>();
        if(data != null){
            for(List<String> row : data){
                accounts.add(new Account(row.get(0), Double.parseDouble(row.get(1)), row.get(2), row.get(3)));
            }
        }
        return accounts;
    }

    protected static List<List<String>> toCSVData(List<Account> accounts){
        List<List<String>> data = new ArrayList<>();
        for(Account a : accounts){
            List<String> row = new ArrayList<>();
            row.add(a.accountId);
            row.add(String.valueOf(a.balance));
            row.add(a.cardTypeId);
            row.add(a.accountType);
            data.add(row);
        }
        return data;
    }

    protected double getBalance() {
        return this.balance;
    }

    private boolean checkDailyLimit(String operationType, double amount, boolean isOwnAccount) {
        CardType ct = CardType.getCardType(this.cardTypeId);
        if (ct == null) {
            System.out.println("Card Type not found");
            return false;
        }

        // Get today’s start/end
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        // Fetch all account transactions today
        List<Transaction> today = Transaction.getHistory(this.accountId, start, end).stream()
                .filter(t -> t.status.equalsIgnoreCase("SUCCESS")).toList();

        double used = 0;

        switch (operationType) {
            case "WITHDRAW":
                used = today.stream()
                        .filter(t -> t.debitedAccount != null
                                && t.debitedAccount.equalsIgnoreCase(this.accountId))
                        .mapToDouble(t -> t.amount)
                        .sum();

                if (used + amount > ct.withdrawLPD_Limit) {
                    System.out.println("Daily withdrawal limit reached");
                    return false;
                }
                break;

            case "TRANSFER":
                if (isOwnAccount) {
                    used = today.stream()
                            .filter(t -> t.debitedAccount != null
                                    && t.debitedAccount.equalsIgnoreCase(this.accountId))
                            .mapToDouble(t -> t.amount)
                            .sum();

                    if (used + amount > ct.transferLPDOwn_Limit) {
                        System.out.println("Daily own-transfer limit reached");
                        return false;
                    }

                } else {
                    used = today.stream()
                            .filter(t -> t.debitedAccount != null
                                    && t.debitedAccount.equalsIgnoreCase(this.accountId))
                            .mapToDouble(t -> t.amount)
                            .sum();

                    if (used + amount > ct.transferLPD_Limit) {
                        System.out.println("Daily transfer limit reached");
                        return false;
                    }
                }
                break;

            case "DEPOSIT":
                if (isOwnAccount) {
                    used = today.stream()
                            .filter(t -> t.creditedAccount != null
                                    && t.creditedAccount.equalsIgnoreCase(this.accountId))
                            .mapToDouble(t -> t.amount)
                            .sum();

                    if (used + amount > ct.depositLPDOwn_Limit) {
                        System.out.println("Daily own-deposit limit reached");
                        return false;
                    }

                } else {
                    used = today.stream()
                            .filter(t -> t.creditedAccount != null
                                    && t.creditedAccount.equalsIgnoreCase(this.accountId))
                            .mapToDouble(t -> t.amount)
                            .sum();

                    if (used + amount > ct.depositLPD_Limit) {
                        System.out.println("Daily deposit limit reached");
                        return false;
                    }
                }
                break;
        }

        return true; // passed all checks
    }

    protected boolean transfer(String targetAccountId, double amount) {
        Account target = Account.getAccount(targetAccountId);

        if (target == null) {
            System.out.println("Target account not found");
            Transaction.create(this.accountId, targetAccountId, amount, "FAILED_NO_TARGET");
            return false;
        }

        if (amount > this.balance) {
            System.out.println("Insufficient funds");
            Transaction.create(this.accountId, targetAccountId, amount, "FAILED_FUNDS");
            return false;
        }

        boolean isOwn = this.cardTypeId.equalsIgnoreCase(target.cardTypeId);

        if (!checkDailyLimit("TRANSFER", amount, isOwn)) {
            Transaction.create(this.accountId, targetAccountId, amount, "FAILED_LIMIT");
            return false;
        }

        // Perform transfer in memory
        this.balance -= amount;
        target.balance += amount;

        if (this.update() && target.update()) {
            Transaction.create(this.accountId, targetAccountId, amount, "SUCCESS");
            return true;
        } else {
            // rollback
            this.balance += amount;
            target.balance -= amount;

            this.update();
            target.update();

            Transaction.create(this.accountId, targetAccountId, amount, "FAILED_UPDATE");
            return false;
        }
    }
    protected boolean withdraw(double amount) {

        if (this.balance - amount < -100) {
            System.out.println("Insufficient funds");
            Transaction.create(this.accountId, null, amount, "FAILED_FUNDS");
            return false;
        }

        if (!checkDailyLimit("WITHDRAW", amount, false)) {
            Transaction.create(this.accountId, null, amount, "FAILED_LIMIT");
            return false;
        }

        if (this.overdraftCount >= 2 && this.overdraftFees > 0){
            System.out.println("Account is locked, please pay overdraft fees");
            System.out.println("Overdraft fees: " + this.overdraftFees);
            return false;
        }

//        if(this.balance - amount < -100){
//            System.out.println("You cannot withdraw more than 100 when your balance is negative");
//            return false;
//        }
        if(this.balance - amount >= -100){
            this.chargeOverdraft();
            System.out.println("Balance is negative; Overdraft is charged");
            System.out.println("Balance: " + this.balance);
            System.out.println("Overdraft fees: " + this.overdraftFees);
        }
        this.balance -= amount;


        if (this.update()) {
            Transaction.create(this.accountId, null, amount, "SUCCESS");
            return true;
        } else {
            this.balance += amount;  // rollback
            Transaction.create(this.accountId, null, amount, "FAILED_UPDATE");
            return false;
        }
    }
    protected boolean deposit(double amount) {

        if (!checkDailyLimit("DEPOSIT", amount, false)) {
            Transaction.create(null, this.accountId, amount, "FAILED_LIMIT");
            return false;
        }

        this.balance += amount;

        if (this.update()) {
            Transaction.create(null, this.accountId, amount, "SUCCESS");
            return true;
        } else {
            this.balance -= amount;  // rollback
            Transaction.create(null, this.accountId, amount, "FAILED_UPDATE");
            return false;
        }
    }

    private void chargeOverdraft(){
        double overdraft = 35;
        this.overdraftCount++;
        this.overdraftFees += 35;
    }


}
