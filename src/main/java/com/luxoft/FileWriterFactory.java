package com.luxoft;

import com.luxoft.writer.*;

public class FileWriterFactory {

    private static final RandomByteGenerator randomByteGenerator = new RandomByteGenerator();

    public static FileWriter create(FileWriterVersion version) {
        return switch (version) {
            case VERSION_0 -> new FileWriterV0(randomByteGenerator);
            case VERSION_1 -> new FileWriterV1(randomByteGenerator);
            case VERSION_2 -> new FileWriterV2(randomByteGenerator);
            case VERSION_3 -> new FileWriterV3(randomByteGenerator);
            case VERSION_4 -> new FileWriterV4(randomByteGenerator);
            case VERSION_5 -> new FileWriterV5(randomByteGenerator);
            case VERSION_6 -> new FileWriterV6(randomByteGenerator);
            case VERSION_7 -> new FileWriterV7(randomByteGenerator);
        };
    }
}
