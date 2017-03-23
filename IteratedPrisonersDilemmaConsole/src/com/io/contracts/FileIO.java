package com.io.contracts;

import java.io.IOException;

public interface FileIO {

    String read(String file) throws IOException;

    void write(String fileContent, String file, boolean append) throws IOException;
}