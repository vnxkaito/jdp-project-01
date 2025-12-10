package com.ga.java.project01;

public class WithdrawAction implements Action {

    @Override
    public boolean execute(String[] args) {
        Session session = Session.get();
        if(session == null){
            System.out.println("Please login first!");
            return false;
        }

        if(args.length < 1){
            System.out.println("Usage: withdraw <amount>");
            return false;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[0]);
        } catch (NumberFormatException e){
            System.out.println("Invalid amount.");
            return false;
        }

        Account acc = Account.getAccountByUserId(session.getUserId());
        if(acc == null){
            System.out.println("Account not found.");
            return false;
        }

        if(acc.withdraw(amount)){
            System.out.println("Withdrawal successful! Balance: " + acc.getBalance());
            return true;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }

    @Override
    public String getName() {
        return "withdraw";
    }
}
