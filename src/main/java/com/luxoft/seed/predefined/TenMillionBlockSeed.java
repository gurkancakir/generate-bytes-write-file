package com.luxoft.seed.predefined;

import com.luxoft.seed.Seed;

import java.util.ArrayList;
import java.util.List;

public class TenMillionBlockSeed implements Seed {

    private static final List<Long> seeds = new ArrayList<>();

    static {
        seeds.add(25214903917L);
        seeds.add(235795687876493L);
        seeds.add(124772739397805L);
        seeds.add(250418919446989L);
        seeds.add(43066040306413L);
        seeds.add(237813290912781L);
        seeds.add(69063189044525L);
        seeds.add(38481974705741L);
        seeds.add(264022771478381L);
        seeds.add(180248538411149L);
    }


    @Override
    public long get(int index) {
        return seeds.get(index);
    }

    @Override
    public boolean support(int chunkSize) {
        return 10_000_000 == chunkSize;
    }
}
