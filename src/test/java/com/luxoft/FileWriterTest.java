package com.luxoft;

import com.luxoft.writer.FileWriter;
import com.luxoft.writer.FileWriterVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileWriterTest {

    private FileWriter fileWriter;

    private File file = null;

    @Disabled
    @BeforeEach
    public void beforeEachTest() throws IOException {
        Path path = Files.createTempFile("GURKAN_CAKIR_", "_LUXOFT");
        System.out.println("Output file path : " + path.toAbsolutePath());
        file = path.toFile();
    }

    public static Stream<Arguments> provideTotalAndChunkSize() {
        return Stream.of(
                Arguments.of(10_000_000, 1_000_000)
        );
    }


    @Disabled
    @ParameterizedTest
    //@CsvSource({"10_000_000, 1_000"})
    @MethodSource("provideTotalAndChunkSize")
    public void fileWriterV1(int totalSize, int chunkSize) throws IOException {
        fileWriter = FileWriterFactory.create(FileWriterVersion.VERSION_1);
        fileWriter.writeToFile(file, totalSize, chunkSize);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("provideTotalAndChunkSize")
    public void fileWriterV2(int totalSize, int chunkSize) throws IOException {
        fileWriter = FileWriterFactory.create(FileWriterVersion.VERSION_2);
        fileWriter.writeToFile(file, totalSize, chunkSize);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("provideTotalAndChunkSize")
    public void fileWriterV3(int totalSize, int chunkSize) throws IOException {
        fileWriter = FileWriterFactory.create(FileWriterVersion.VERSION_3);
        fileWriter.writeToFile(file, totalSize, chunkSize);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("provideTotalAndChunkSize")
    public void fileWriterV4(int totalSize, int chunkSize) throws IOException {
        fileWriter = FileWriterFactory.create(FileWriterVersion.VERSION_4);
        fileWriter.writeToFile(file, totalSize, chunkSize);
    }


    @Disabled
    @ParameterizedTest
    @MethodSource("provideTotalAndChunkSize")
    public void fileWriterV5(int totalSize, int chunkSize) throws IOException {
        fileWriter = FileWriterFactory.create(FileWriterVersion.VERSION_5);
        fileWriter.writeToFile(file, totalSize, chunkSize);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("provideTotalAndChunkSize")
    public void fileWriterV6(int totalSize, int chunkSize) throws IOException {
        fileWriter = FileWriterFactory.create(FileWriterVersion.VERSION_6);
        fileWriter.writeToFile(file, totalSize, chunkSize);
    }


}
