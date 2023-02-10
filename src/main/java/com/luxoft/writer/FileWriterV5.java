package com.luxoft.writer;

import com.luxoft.RandomByteGenerator;
import com.luxoft.seed.SeedGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class FileWriterV5 implements FileWriter {

    private final RandomByteGenerator randomByteGenerator;

    public FileWriterV5(RandomByteGenerator randomByteGenerator) {
        this.randomByteGenerator = randomByteGenerator;
    }

    @Override
    public void writeToFile(File outputFile, int totalSize, int chunkSize) throws IOException {
        long startTime = System.nanoTime();

        try (var writeChannel = AsynchronousFileChannel.open(outputFile.toPath(), StandardOpenOption.WRITE)) {
            int limit = (int) Math.ceil((double) totalSize / chunkSize);

            var seedGenerator = new SeedGenerator(0, chunkSize);
            var futureList = IntStream.iterate(0, i -> i + chunkSize)
                    .limit(limit)
                    //.parallel()
                    .mapToObj(index -> writeChannel.write(ByteBuffer.wrap(randomByteGenerator.generateUsingSeed(seedGenerator.next(), chunkSize)), index))
                    .toList();

            //System.out.println("Write Future Size : " + futureList.size());
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
