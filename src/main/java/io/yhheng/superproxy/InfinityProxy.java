package io.yhheng.superproxy;

import io.yhheng.superproxy.config.ConfigLoader;
import io.yhheng.superproxy.config.ServerConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InfinityProxy {

    public static void main(String[] args) throws IOException {
        // 1 加载json的配置，形成一个ServerConfig的列表
        String configJson = Files.readString(Paths.get("./config.json"));
        ServerConfig serverConfig = ConfigLoader.loadFromJson(configJson);

        // 2 根据ServerConfig的列表初始化一个InfinityProxyServer实例 并且启动它
        Server server = new Server(serverConfig);
    }
}
