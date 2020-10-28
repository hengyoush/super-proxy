package io.yhheng.superproxy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = args[0];
        Infinity infinity = Infinity.parse(Files.readString(Paths.get(path)));
    }
}
