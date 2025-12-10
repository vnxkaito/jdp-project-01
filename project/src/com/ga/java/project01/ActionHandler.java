package com.ga.java.project01;

import java.util.HashMap;
import java.util.Map;

public class ActionHandler {
    private static final Map<String, Action> actions = new HashMap<>();

    static {
        // Register all actions
        actions.put("withdraw", new WithdrawAction());
        actions.put("deposit", new DepositAction());
        actions.put("transfer", new TransferAction());
        actions.put("login", new LoginAction());
        actions.put("set-password", new SetPasswordAction());
        actions.put("display-history", new DisplayHistoryAction());
        actions.put("display-history-past-n", new DisplayHistoryPastNAction());
    }

    public static boolean execute(String commandName, String[] args) {
        Action action = actions.get(commandName);

        if (action == null) {
            System.out.println("Unknown command: " + commandName);
            return false;
        }

        if (!commandName.equals("login")) {
            Session session = Session.get();

            if (session == null) {
                System.out.println("No active session. Please login first.");
                return false;
            }

            if (!Authorization.isAuthorized(session, commandName)) {
                System.out.println("You are not authorized to run this command.");
                return false;
            }
        }

        try {
            return action.execute(args);
        } catch (Exception e) {
            System.out.println("An error occurred while executing the command: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
