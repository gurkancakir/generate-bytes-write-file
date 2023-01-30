package com.luxoft;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class FileWriter {

    public void writeToFile(byte[] contentInBytes, File file) throws IOException {
        long startTime = System.nanoTime();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw"); FileChannel fileChannel = randomAccessFile.getChannel()) {

            int bufferSize = 1000;
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);

            int chunkSize = 1000;
            int limit = (int) Math.ceil((double) contentInBytes.length / chunkSize);

            AtomicInteger iterate = new AtomicInteger();
            IntStream.iterate(0, i -> i + chunkSize)
                    .limit(limit)
                    .mapToObj(index -> Arrays.copyOfRange(contentInBytes, index, Math.min(contentInBytes.length, index + chunkSize)))
                    .forEach(bytes -> {
                        try {
                            fileChannel.write(buff.put(bytes), iterate.getAndIncrement() * chunkSize);
                            buff.clear();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        long stopTime = System.nanoTime();
        System.out.printf("Write Time V3 : %2.2f ms \n", nanoTimeToMillis(startTime, stopTime));
    }


    private float nanoTimeToMillis(long startTime, long stopTime) {
        return (stopTime - startTime) / 1_000_000f;
    }
}
