package com.luxoft;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomByteGenerator {

    public byte[] generate(int byteSize) {
        final byte[] bytes = new byte[byteSize];
        ThreadLocalRandom.current().nextBytes(bytes);
        //new Random().nextBytes(bytes);
        return bytes;
    }
}
