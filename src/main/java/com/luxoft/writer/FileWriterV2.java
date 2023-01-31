package com.luxoft.writer;

import com.luxoft.RandomByteGenerator;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class FileWriterV2 implements FileWriter {

    private final RandomByteGenerator randomByteGenerator;

    public FileWriterV2(RandomByteGenerator randomByteGenerator) {
        this.randomByteGenerator = randomByteGenerator;
    }

    @Override
    public void writeToFile(File outputFile, int totalSize, int chunkSize) throws IOException {
        long startTime = System.nanoTime();

        byte[] contentInBytes = randomByteGenerator.generate(totalSize);

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile, "rw");
             FileChannel fileChannel = randomAccessFile.getChannel()) {

            int limit = (int) Math.ceil((double) contentInBytes.length / chunkSize);

            AtomicInteger iterate = new AtomicInteger();
            IntStream.iterate(0, i -> i + chunkSize)
                    .limit(limit)
                    .mapToObj(index -> Arrays.copyOfRange(contentInBytes, index, Math.min(contentInBytes.length, index + chunkSize)))
                    .parallel()
                    .forEach(bytes -> {
                        try {
                            fileChannel.write(ByteBuffer.wrap(bytes), iterate.getAndIncrement() * chunkSize);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        long stopTime = System.nanoTime();
        showExecutionTime(startTime, stopTime, outputFile.getPath());
    }
}
