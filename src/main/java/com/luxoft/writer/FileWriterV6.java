package com.luxoft.writer;

import com.luxoft.RandomByteGenerator;
import com.luxoft.seed.SeedGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;

public class FileWriterV6 implements FileWriter {

    private final RandomByteGenerator randomByteGenerator;

    public FileWriterV6(RandomByteGenerator randomByteGenerator) {
        this.randomByteGenerator = randomByteGenerator;
    }

    @Override
    public void writeToFile(File outputFile, int totalSize, int chunkSize) throws IOException {

        long startTime = System.nanoTime();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        int sizePerThread = totalSize / numberOfThreads;

        var seedGenerator = new SeedGenerator(0, sizePerThread);

        try(var writeChannel = AsynchronousFileChannel.open(outputFile.toPath(), StandardOpenOption.WRITE)) {
            Thread[] threads = new Thread[numberOfThreads];
            for (int i = 0; i < numberOfThreads; i++) {
                threads[i] = new Thread(new FileWriteRunnable(randomByteGenerator, writeChannel,
                        sizePerThread, i * sizePerThread, seedGenerator.next()));
                threads[i].start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
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
