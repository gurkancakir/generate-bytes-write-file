package com.luxoft.seed;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

public class SeedGenerator {

    private final static long multiplier = 0x5DEECE66DL;
    private final static long addend = 0xBL;
    private final static long mask = (1L << 48) - 1;

    int counter = 0;
    int chunkSize = 0;
    int chunkSizeByte = 0;

    int totalSize = 0;

    private Queue<Long> seeds = new LinkedList<>();

    private final AtomicLong seed = new AtomicLong(0);

    public SeedGenerator(long seed) {
        this.seed.set(initialScramble(seed));
        seeds.add(this.seed.get());
    }

    public SeedGenerator(long seed, int chunkSize) {
        this(seed);
        this.chunkSize = chunkSize;
        this.chunkSizeByte = chunkSize / 4;
    }

    private long initialScramble(long seed) {
        return (seed ^ multiplier) & mask;
    }

    public void generate() {
        long oldseed, nextseed;
        AtomicLong seed = this.seed;
        do {
            oldseed = seed.get();
            nextseed = (oldseed * multiplier + addend) & mask;
        } while (!seed.compareAndSet(oldseed, nextseed));

        if (++counter % chunkSizeByte == 0) {
            seeds.add(this.seed.get());
        }
    }

    public Long next(int chunkSize) {
        this.chunkSize = chunkSize;
        this.chunkSizeByte = chunkSize / 4;
        for (int i = 0; i < chunkSizeByte; i++) {
            generate();
        }
        return this.seeds.poll();
    }

    public Long next() {
        for (int i = 0; i < chunkSizeByte; i++) {
            generate();
        }
        return this.seeds.poll();
    }

    public static void main(String[] args) {
        SeedGenerator generator;
        long s = System.nanoTime();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        int sizePerThread = 100_000_000 / numberOfThreads;
        generator = new SeedGenerator(0);
        for (int i = 0; i < numberOfThreads; i++) {
            System.out.println(generator.next(sizePerThread));
        }
        System.out.println("Time : " + (System.nanoTime() - s));
    }
}
