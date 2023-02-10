package com.luxoft.writer;

import com.luxoft.RandomByteGenerator;
import com.luxoft.seed.SeedGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class FileWriterV7 implements FileWriter {

    private final RandomByteGenerator randomByteGenerator;

    public FileWriterV7(RandomByteGenerator randomByteGenerator) {
        this.randomByteGenerator = randomByteGenerator;
    }

    @Override
    public void writeToFile(File outputFile, int totalSize, int chunkSize) throws IOException {

        long startTime = System.nanoTime();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        try(var writeChannel = AsynchronousFileChannel.open(outputFile.toPath(), StandardOpenOption.WRITE)) {

            int limit = (int) Math.ceil((double) totalSize / chunkSize);
            var seedGenerator = new SeedGenerator(0, chunkSize);
            var futureList = IntStream.iterate(0, i -> i + chunkSize)
                    .limit(limit)
                    .mapToObj(index -> executorService.submit(
                            new FileWriteRunnable(randomByteGenerator, writeChannel,
                                    chunkSize, index, seedGenerator.next())))
                    .toList();

            for (Future<?> future : futureList) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            executorService.shutdown();
        }
        long stopTime = System.nanoTime();
        showExecutionTime(startTime, stopTime, outputFile.getPath());
    }


    static class FileWriteRunnable implements Runnable {

        private final RandomByteGenerator randomByteGenerator;
        private final AsynchronousFileChannel writeChannel;
        private final int chunkSize;
        private final int position;

        private final long startSeed;

        public FileWriteRunnable(RandomByteGenerator randomByteGenerator, AsynchronousFileChannel writeChannel,
                                 int chunkSize, int position, long startSeed) {
            this.randomByteGenerator = randomByteGenerator;
            this.writeChannel = writeChannel;
            this.chunkSize = chunkSize;
            this.position = position;
            this.startSeed = startSeed;
        }

        @Override
        public void run() {
            try {
                writeChannel.write(ByteBuffer.wrap(randomByteGenerator.generateUsingSeed(startSeed, chunkSize)), position).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
