package com.luxoft.seed.predefined;

import com.luxoft.seed.Seed;

import java.util.ArrayList;
import java.util.List;

public class SixMillionBlockSeed implements Seed {

    private static final List<Long> seeds = new ArrayList<>();

    static {
        seeds.add(25214903917L);
        seeds.add(242630530160321L);
        seeds.add(256394774253653L);
        seeds.add(225665095657257L);
        seeds.add(275038241746749L);
        seeds.add(212437650701457L);
        seeds.add(189234130461477L);
        seeds.add(29414913881849L);
        seeds.add(237813290912781L);
        seeds.add(80498046318177L);
        seeds.add(123934495672821L);
        seeds.add(158591082830537L);
        seeds.add(249632498121949L);
        seeds.add(164986588234801L);
        seeds.add(231940897817797L);
        seeds.add(160661902987929L);
    }


    @Override
    public long get(int index) {
        return seeds.get(index);
    }

    @Override
    public boolean support(int chunkSize) {
        return 6_250_000 == chunkSize;
    }
}
