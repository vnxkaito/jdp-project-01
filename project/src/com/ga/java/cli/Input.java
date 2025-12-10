package com.ga.java.cli;

public class Input {
//    Argument[] arguments;
    String command;
    String[] args; // only the values

    public Input(String command, String[] arguments){
        this.args = arguments;
        this.command = command;
    }
}
