package com.luxoft.seed;

import com.luxoft.seed.predefined.OneMillionBlockSeed;
import com.luxoft.seed.predefined.SixMillionBlockSeed;
import com.luxoft.seed.predefined.TenMillionBlockSeed;

import java.util.ArrayList;
import java.util.List;

public final class SeedFactory {

    private static final List<Seed> seeds = new ArrayList<>();

    static {
        seeds.add(new OneMillionBlockSeed());
        seeds.add(new SixMillionBlockSeed());
        seeds.add(new TenMillionBlockSeed());
    }

    public static Seed create(int chunkSize) {
        for (Seed seed : seeds) {
            if (seed.support(chunkSize))
                return seed;
        }
        return null;
    }
}
