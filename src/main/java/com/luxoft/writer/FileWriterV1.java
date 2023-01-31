package com.luxoft.writer;

import com.luxoft.RandomByteGenerator;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileWriterV1 implements FileWriter {

    private final RandomByteGenerator randomByteGenerator;

    public FileWriterV1(RandomByteGenerator randomByteGenerator) {
        this.randomByteGenerator = randomByteGenerator;
    }

    @Override
    public void writeToFile(File outputFile, int totalSize, int chunkSize) throws IOException {
        long startTime = System.nanoTime();

        byte[] contentInBytes = randomByteGenerator.generate(totalSize);

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile, "rw");
             FileChannel fileChannel = randomAccessFile.getChannel()) {
            ByteBuffer buff = ByteBuffer.wrap(contentInBytes);
            fileChannel.write(buff);
        }
        long stopTime = System.nanoTime();
        showExecutionTime(startTime, stopTime, outputFile.getPath());
    }
}
