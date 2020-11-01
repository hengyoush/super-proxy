package io.yhheng.superproxy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String path = args[0];
        Infinity infinity = Infinity.parse(new String(Files.readAllBytes(Paths.get(Main.class.getClassLoader().getResource(path).toURI()))));

        infinity.startup();
    }
}
