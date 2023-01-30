package com.luxoft;

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

public class FileWriter {

    public void writeToFile(byte[] contentInBytes, File file) throws IOException {
        long startTime = System.nanoTime();

        try (var writeChannel = AsynchronousFileChannel.open(file.toPath(), StandardOpenOption.WRITE)) {
            int chunkSize = 10_000_000;
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
        System.out.printf("Write Time V4 : %2.2f ms \n", nanoTimeToMillis(startTime, stopTime));
    }


    private float nanoTimeToMillis(long startTime, long stopTime) {
        return (stopTime - startTime) / 1_000_000f;
    }
}
