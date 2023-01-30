package com.luxoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class FileWriter {

    public void writeToFile(File outputFile, int totalSize, int chunkSize, RandomByteGenerator randomByteGenerator) throws IOException {
        long startTime = System.nanoTime();

        try (var writeChannel = AsynchronousFileChannel.open(outputFile.toPath(), StandardOpenOption.WRITE)) {
            int limit = (int) Math.ceil((double) totalSize / chunkSize);

            var iterate = new AtomicInteger();
            var futureList = IntStream.iterate(0, i -> i + chunkSize)
                    .limit(limit)
                    .mapToObj(index -> randomByteGenerator.generate(chunkSize))
                    .parallel()
                    .map(bytes -> writeChannel.write(ByteBuffer.wrap(bytes), iterate.getAndIncrement() * chunkSize)).toList();

            System.out.println("Write Future Size : " + futureList.size());
            for (Future<Integer> future : futureList) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        long stopTime = System.nanoTime();
        System.out.printf("Write Time V5 : %2.2f ms \n", nanoTimeToMillis(startTime, stopTime));
    }


    private float nanoTimeToMillis(long startTime, long stopTime) {
        return (stopTime - startTime) / 1_000_000f;
    }
}
