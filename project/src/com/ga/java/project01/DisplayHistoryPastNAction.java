package com.ga.java.project01;

import java.time.LocalDateTime;
import java.util.List;

public class DisplayHistoryPastNAction implements Action {

    @Override
    public boolean execute(String[] args) {
        Session session = Session.get();
        if(session == null){
            System.out.println("Please login first!");
            return false;
        }
        if(!args[0].equalsIgnoreCase(session.getUserId())){
            System.out.println("You can only do this command on your own accounts");
        }
        if(args.length < 5){
            System.out.println("Usage: display-history-past-n <account> <years> <months> <days> <hours>");
            return false;
        }


        String accountId = args[0];
        int years = Integer.parseInt(args[1]);
        int months = Integer.parseInt(args[2]);
        int days = Integer.parseInt(args[3]);
        int hours = Integer.parseInt(args[4]);

        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusYears(years).minusMonths(months).minusDays(days).minusHours(hours);

        List<Transaction> history = Transaction.getHistory(accountId, start, end);
        history.forEach(System.out::println);
        return true;
    }

    @Override
    public String getName() {
        return "display-history-past-n";
    }
}
