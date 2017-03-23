package com.io.contracts;

public interface ConsoleIO {
    String readLine();

    void writeLine(String output);

    void writeLine(String format, String output);
}
