package com.luxoft.writer;

import com.luxoft.RandomByteGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriterV0 implements FileWriter {

    private final RandomByteGenerator randomByteGenerator;

    public FileWriterV0(RandomByteGenerator randomByteGenerator) {
        this.randomByteGenerator = randomByteGenerator;
    }

    @Override
    public void writeToFile(File outputFile, int totalSize, int chunkSize) throws IOException {
        long startTime = System.nanoTime();

        byte[] contentInBytes = randomByteGenerator.generate(totalSize);

        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            fileOutputStream.write(contentInBytes);
            fileOutputStream.flush();
        }
        long stopTime = System.nanoTime();
        showExecutionTime(startTime, stopTime, outputFile.getPath());
    }
}
