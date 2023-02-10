package com.luxoft;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MultiThreadRandom extends Random {
    private final AtomicLong seed = new AtomicLong(0);

    private final static long multiplier = 0x5DEECE66DL;
    private final static long addend = 0xBL;
    private final static long mask = (1L << 48) - 1;

    int counter = 0;

    public MultiThreadRandom() {
       this(seedUniquifier() ^ System.nanoTime());
    }

    private static long seedUniquifier() {
        // L'Ecuyer, "Tables of Linear Congruential Generators of
        // Different Sizes and Good Lattice Structure", 1999
        for (;;) {
            long current = seedUniquifier.get();
            long next = current * 1181783497276652981L;
            if (seedUniquifier.compareAndSet(current, next))
                return next;
        }
    }

    private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);
    public MultiThreadRandom(long seed) {
        changeSeed(initialScramble(seed));
        //System.out.println("start seed : " + this.seed.get());
    }
    @Override
    protected int next(int bits) {
        long oldseed, nextseed;
        AtomicLong seed = this.seed;
        do {
            oldseed = seed.get();
            nextseed = (oldseed * multiplier + addend) & mask;
        } while (!seed.compareAndSet(oldseed, nextseed));
        return (int) (nextseed >>> (48 - bits));
    }

    public synchronized void changeSeed(long seedNew) {
        this.seed.set(seedNew);
    }

    private long initialScramble(long seed) {
        return (seed ^ multiplier) & mask;
    }
}
