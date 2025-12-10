package com.ga.java.project01;

import com.ga.java.cli.Input;

import java.util.ArrayList;


public class InputParser {
    public static void main(String[] args) {
        Input input = parse("testcommand -arg1 arg1Value");
        System.out.println();
    }
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
        String command = "";
        ArrayList<String> args = new ArrayList<>();
        boolean firstWord = true;
        for(int i = 0; i<words.length; i++){
            if(firstWord){
                command = words[i];
                firstWord = false;
            }else {
                args.add(words[i]);
            }
        }
        return new Input(command, args.toArray(new String[]{}));
    }
}
