package com.luxoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileWriter {

    public void writeToFile(byte[] contentInBytes, File file) throws IOException {
        long startTime = System.nanoTime();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw"); FileChannel fileChannel = randomAccessFile.getChannel()) {
            ByteBuffer buff = ByteBuffer.wrap(contentInBytes);
            fileChannel.write(buff);
        }
        long stopTime = System.nanoTime();
        System.out.printf("Write Time V2 : %2.2f ms \n", nanoTimeToMillis(startTime, stopTime));
    }


    private float nanoTimeToMillis(long startTime, long stopTime) {
        return (stopTime - startTime) / 1_000_000f;
    }
}
