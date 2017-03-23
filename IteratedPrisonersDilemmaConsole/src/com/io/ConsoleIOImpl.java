package com.io;

import com.io.contracts.ConsoleIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleIOImpl implements ConsoleIO {
    private BufferedReader consoleReader;

    public ConsoleIOImpl() {
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String readLine() {
        String inputLine = null;

        try {
            inputLine = this.consoleReader.readLine();
        } catch (IOException e) {
            return e.getMessage();
        }

        return inputLine;
    }

    @Override
    public void writeLine(String output) {
        System.out.println(output);
    }

    @Override
    public void writeLine(String format, String output) {
        System.out.println(String.format(format, output));
    }
}
