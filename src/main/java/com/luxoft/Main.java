package com.luxoft;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {

        Path path = Files.createTempFile("GURKAN_CAKIR_", "_LUXOFT");
        System.out.println("Output file path : " + path.toAbsolutePath());
        File file = path.toFile();

        RandomByteGenerator randomByteGenerator = new RandomByteGenerator();
        FileWriter fileWriter = new FileWriter();

        int chunkSize = 10_000_000;
        int totalSize = 100_000_000;
        fileWriter.writeToFile(file, totalSize, chunkSize, randomByteGenerator);

    }
}
