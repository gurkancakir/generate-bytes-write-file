package com.luxoft.writer;

import com.luxoft.RandomByteGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class FileWriterV3 implements FileWriter {

    private final RandomByteGenerator randomByteGenerator;

    public FileWriterV3(RandomByteGenerator randomByteGenerator) {
        this.randomByteGenerator = randomByteGenerator;
    }

    @Override
    public void writeToFile(File outputFile, int totalSize, int chunkSize) throws IOException {
        long startTime = System.nanoTime();

        byte[] contentInBytes = randomByteGenerator.generate(totalSize);

        try (var writeChannel = AsynchronousFileChannel.open(outputFile.toPath(), StandardOpenOption.WRITE)) {
            int limit = (int) Math.ceil((double) contentInBytes.length / chunkSize);

            AtomicInteger iterate = new AtomicInteger();
            List<Future<Integer>> futureList = IntStream.iterate(0, i -> i + chunkSize)
                    .limit(limit)
                    .mapToObj(index -> Arrays.copyOfRange(contentInBytes, index, Math.min(contentInBytes.length, index + chunkSize)))
                    .parallel()
                    .map(bytes -> writeChannel.write(ByteBuffer.wrap(bytes), iterate.getAndIncrement() * chunkSize))
                    .toList();

            for (Future<Integer> future : futureList) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        long stopTime = System.nanoTime();
        showExecutionTime(startTime, stopTime, outputFile.getPath());
    }
}
