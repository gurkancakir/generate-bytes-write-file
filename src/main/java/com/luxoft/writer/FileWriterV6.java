package com.luxoft.writer;

import com.luxoft.RandomByteGenerator;
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

        try(var writeChannel = AsynchronousFileChannel.open(outputFile.toPath(), StandardOpenOption.WRITE)) {
            Thread[] threads = new Thread[numberOfThreads];
            for (int i = 0; i < numberOfThreads; i++) {
                threads[i] = new Thread(new FileWriteRunnable(randomByteGenerator, writeChannel, sizePerThread, i * sizePerThread));
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

        public FileWriteRunnable(RandomByteGenerator randomByteGenerator, AsynchronousFileChannel writeChannel,
                                 int chunkSize, int position) {
            this.randomByteGenerator = randomByteGenerator;
            this.writeChannel = writeChannel;
            this.chunkSize = chunkSize;
            this.position = position;
        }

        @Override
        public void run() {
            try {
                writeChannel.write(ByteBuffer.wrap(randomByteGenerator.generate(chunkSize)), position).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
