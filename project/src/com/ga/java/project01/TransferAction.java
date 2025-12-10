package com.ga.java.project01;

public class TransferAction implements Action {

    @Override
    public boolean execute(String[] args) {
        Session session = Session.get();
        if(session == null){
            System.out.println("Please login first!");
            return false;
        }

        if(args.length < 2){
            System.out.println("Usage: transfer <amount> <recv-account>");
            return false;
        }

        double amount;
        String recvAccountId = args[1];
        try {
            amount = Double.parseDouble(args[0]);
        } catch (NumberFormatException e){
            System.out.println("Invalid amount.");
            return false;
        }

        Account senderAcc = Account.getAccountByUserId(session.getUserId());
        Account recvAcc = Account.getAccount(recvAccountId);

        if(senderAcc == null || recvAcc == null){
            System.out.println("Account not found.");
            return false;
        }

        if(senderAcc.withdraw(amount)){
            recvAcc.deposit(amount);
            Transaction.create(senderAcc.accountId, recvAcc.accountId, amount, "SUCCESS");
            System.out.println("Transfer successful!");
            return true;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }

    @Override
    public String getName() {
        return "transfer";
    }
}
