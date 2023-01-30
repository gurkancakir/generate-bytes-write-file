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


        byte[] contentInBytes = generateAllBytes(randomByteGenerator);
        fileWriter.writeToFile(contentInBytes, file);

    }

    private static byte[] generateAllBytes(RandomByteGenerator randomByteGenerator) {
        long startTime = System.nanoTime();
        byte[] contentInBytes = randomByteGenerator.generate(100_000_000);
        long stopTime = System.nanoTime();
        System.out.printf("Generate Time : %2.2f ms \n", (stopTime - startTime) / 1_000_000f);
        return contentInBytes;
    }

}
