package io.yhheng;

import io.yhheng.superproxy.Infinity;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainTest {
    @Test
    public void testMain() throws IOException {
        Infinity infinity = Infinity.parse(Files.readString(Paths.get("./config.json")));

        infinity.startup();
    }
}
