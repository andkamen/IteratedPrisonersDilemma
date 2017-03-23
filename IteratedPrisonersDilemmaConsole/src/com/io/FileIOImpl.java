package com.io;

import com.io.contracts.FileIO;

import java.io.*;

public class FileIOImpl implements FileIO {

    @Override
    public String read(String file) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (
                InputStream inputStream = getClass().getResourceAsStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line);
            }
        }

        return fileContent.toString();
    }

    @Override
    public void write(String fileContent, String file, boolean append) throws IOException {
        try (
                OutputStream outputStream = new FileOutputStream(file,append);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))
        ) {
            bufferedWriter.write(fileContent);
        }
    }
}
