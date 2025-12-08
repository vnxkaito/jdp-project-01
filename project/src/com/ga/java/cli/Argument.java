package com.ga.java.cli;

public class Argument {
    private String argName;
    private String argValue;

    private static boolean checkArgumentsSyntaxCorrect(String argName, String argValue){
        boolean isValid = true;
        // to be implemented
        return isValid;
    }
    private static boolean checkArgumentsExists(String argName, String argValue){
        boolean isValid = true;
        // to be implemented
        return isValid;
    }
    public Argument createArgument(String argName, String argValue){
        boolean argumentIsValid = true;
        argumentIsValid = checkArgumentsExists(argName, argValue);
        argumentIsValid = checkArgumentsSyntaxCorrect(argName, argValue);
        Argument argument = null;
        if(argumentIsValid){
            argument = new Argument(argName, argValue);
        }
        return argument;
    }
    private Argument(String argName, String argValue){
        this.argName = argName;
        this.argValue = argValue;
    }

}
