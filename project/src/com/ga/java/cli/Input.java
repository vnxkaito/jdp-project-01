package com.ga.java.cli;

public class Input {
    Argument[] arguments;
    Command command;

    public Input(Command command, Argument[] arguments){
        this.arguments = arguments;
        this.command = command;
    }
}
