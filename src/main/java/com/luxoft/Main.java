package com.luxoft;

import com.luxoft.writer.FileWriter;
import com.luxoft.writer.FileWriterVersion;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {


        //int chunkSize = 10_000_000;
        int totalSize = 100_000_000;
        //runVersion(FileWriterVersion.VERSION_5, totalSize, chunkSize);


        int[] chunks = {10_000_000, 1_000_000, 100_000, 10_000, 1_000};
        for (int chunk : chunks) {
            runAllVersion(totalSize, chunk);
        }
    }

    private static File createTempFile() throws IOException {
        Path path = Files.createTempFile("LUXOFT_GURKAN_CAKIR_", ".bin");
        return path.toFile();
    }

    public static void runAllVersion(int totalSize, int chunkSize) throws IOException {

        System.out.println("================= Execution [totalSize = "+totalSize+", chunkSize = "+chunkSize+" ]  =================");
        FileWriter fileWriter;
        for (FileWriterVersion version : FileWriterVersion.values()) {
            fileWriter = FileWriterFactory.create(version);
            fileWriter.writeToFile(createTempFile(), totalSize, chunkSize);
        }
        System.out.println();
    }

    public static void runVersion(FileWriterVersion version, int totalSize, int chunkSize) throws IOException {
        FileWriter fileWriter = FileWriterFactory.create(version);
        fileWriter.writeToFile(createTempFile(), totalSize, chunkSize);
    }
}
