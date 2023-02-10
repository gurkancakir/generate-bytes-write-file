package com.luxoft;

import com.luxoft.seed.SeedGenerator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class TestSeed {

    public static void main(String[] args) {
        long s = System.nanoTime();
//        MultiThreadRandom random = new MultiThreadRandom(0);
//        byte[] bytes = new byte[100_000_000];
//        random.nextBytes(bytes);
        var seedGenerator = new SeedGenerator(0, 10_000_000);
        Stream.generate(seedGenerator::next)
                .limit(10)
                .forEach(System.out::println);
        long t = System.nanoTime();
        System.out.println("Time : " + (t - s));
//        random.changeSeed(25214903917L);
//        byte[] bytes1 = new byte[100_000_000];
//        random.nextBytes(bytes1);


//        byte[] files1 = Files.readAllBytes(Paths.get("/Users/GCakir/AppData/Local/Temp/LUXOFT_GURKAN_CAKIR_1949582299722012291.bin"));
//        byte[] files2 = Files.readAllBytes(Paths.get("/Users/GCakir/AppData/Local/Temp/LUXOFT_GURKAN_CAKIR_9066522524721859385.bin"));
//        boolean isSameFileContent = Arrays.equals(files2, files1);
//        System.out.println("File content same? : " + isSameFileContent);
    }
}
