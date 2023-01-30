package com.luxoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

    public void writeToFile(byte[] contentInBytes, File file) throws IOException {
        long startTime = System.nanoTime();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(contentInBytes);
            fileOutputStream.flush();
        }
        long stopTime = System.nanoTime();
        System.out.printf("Write Time V1 : %2.2f ms \n", nanoTimeToMillis(startTime, stopTime));
    }


    private float nanoTimeToMillis(long startTime, long stopTime) {
        return (stopTime - startTime) / 1_000_000f;
    }
}
