package com.luxoft.seed;

public interface Seed {

    long get(int index);

    boolean support(int chunkSize);
}
