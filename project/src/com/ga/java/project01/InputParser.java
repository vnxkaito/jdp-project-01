package com.ga.java.project01;

import com.ga.java.cli.Input;

public class InputParser {
    private boolean checkCommandExists(
    ){
        // to be implemented
        return true;
    }


    private boolean isInputValid(String[] words){
        boolean commandExists; // will check the first word and check if it's a valid command
        boolean argumentsSyntaxCorrect; // will check if the arguments follow (-argName argValue) format
        boolean argumentsExists; // will check if the arguments exists for the given command

        boolean inputIsValid = true;

        for(int i = 0; i < words.length; i++){

        }

        return inputIsValid;
    }
    public static Input parse(String inputString){
        String[] words;
        words = inputString.split(" ");
        return null;
    }
}
