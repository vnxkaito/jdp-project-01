package com.ga.java.project01;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DisplayHistoryAction implements Action {

    @Override
    public boolean execute(String[] args) {
        Session session = Session.get();
        if(session == null){
            System.out.println("Please login first!");
            return false;
        }

        if(args.length < 3){
            System.out.println("Usage: display-history <account> <start-date> <end-date>");
            return false;
        }

        String accountId = args[0];
        args[1] = args[1] + "T00:00:00";
        args[2] = args[2] + "T00:00:00";
        LocalDateTime start = LocalDateTime.parse(args[1], DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(args[2], DateTimeFormatter.ISO_DATE_TIME);

        List<Transaction> history = Transaction.getHistory(accountId, start, end);
        history.forEach(System.out::println);
        return true;
    }

    @Override
    public String getName() {
        return "display-history";
    }
}
