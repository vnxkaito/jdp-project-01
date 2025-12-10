package com.ga.java.cli;

import com.ga.java.project01.ActionHandler;
import com.ga.java.project01.InputParser;

import java.util.Scanner;

public class CLI {
    public static void handleNextInput(){
        System.out.println("Please enter your command or enter exit() to close the program or choose from the below menu:");
        System.out.println("login");
        System.out.println("deposit");
        System.out.println("withdraw");
        System.out.println("transfer");
        System.out.println("set-password");
        System.out.println("display-history");
        System.out.println("display-history-past-n");
        System.out.println("---------------------------------------------------------------");
        System.out.println("");

        Scanner scn = new Scanner(System.in);
        String userInputLine = scn.nextLine();
        if(userInputLine.strip().equalsIgnoreCase("exit()")){
            System.exit(0);
        }
        Input userInput = InputParser.parse(userInputLine);
        ActionHandler.execute(userInput.command, userInput.args);
        CLI.handleNextInput();
    }
}
