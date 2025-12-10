package com.ga.java.project01;

public class DepositAction implements Action {

    @Override
    public boolean execute(String[] args) {
        // Get current session
        Session session = Session.get();
        if(session == null){
            System.out.println("Please login first!");
            return false;
        }

        // Validate arguments
        if(args.length < 1){
            System.out.println("Usage: deposit <amount>");
            return false;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[0]);
            if(amount <= 0){
                System.out.println("Amount must be greater than zero.");
                return false;
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid amount. Please enter a numeric value.");
            return false;
        }

        // Get user's account
        Account acc = Account.getAccountByUserId(session.getUserId());
        if(acc == null){
            System.out.println("Account not found.");
            return false;
        }

        // Perform deposit
        acc.deposit(amount);

        // Log the transaction
        Transaction.create(acc.accountId, acc.accountId, amount, "SUCCESS");

        System.out.println("Deposit successful! New balance: " + acc.getBalance());
        return true;
    }

    @Override
    public String getName() {
        return "deposit";
    }
}
