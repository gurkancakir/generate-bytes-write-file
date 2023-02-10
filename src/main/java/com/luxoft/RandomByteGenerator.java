package com.luxoft;


import com.luxoft.seed.Seed;
import com.luxoft.seed.SeedFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RandomByteGenerator {
    private static final int NUMBER_OF_POSSIBLE_SEEDS = 1 << 16;

    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;


    public byte[] generate(int byteSize) {
        final byte[] bytes = new byte[byteSize];
        //ThreadLocalRandom.current().nextBytes(bytes);
        new Random(0).nextBytes(bytes);
        return bytes;
    }

    public byte[] generateNew(int startByteSize, int byteSize) {
        MultiThreadRandom random = new MultiThreadRandom(0);
        Seed seed = SeedFactory.create(byteSize);
        random.changeSeed(seed.get(startByteSize / byteSize));
        final byte[] bytes = new byte[byteSize];
        random.nextBytes(bytes);
        return bytes;
    }

    public byte[] generateUsingSeed(long seed, int byteSize) {
        MultiThreadRandom random = new MultiThreadRandom(0);
        random.changeSeed(seed);
        final byte[] bytes = new byte[byteSize];
        random.nextBytes(bytes);
        return bytes;
    }

}
