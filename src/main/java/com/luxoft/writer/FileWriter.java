package com.luxoft.writer;

import java.io.File;
import java.io.IOException;

public interface FileWriter {

    void writeToFile(File outputFile, int totalSize, int chunkSize) throws IOException;

    default float nanoTimeToMillis(long startTime, long stopTime) {
        return (stopTime - startTime) / 1_000_000f;
    }

    default void showExecutionTime(long startTime, long stopTime, String path) {
        System.out.printf("Execution Time [%s] : %9.2f ms | Output File : %s \n", getClassName(), nanoTimeToMillis(startTime, stopTime), path);
    }

    default String getClassName() {
        String[] fullName = this.getClass().getName().split("\\.");
        return fullName[fullName.length - 1];
    }
}
