package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.proxy.Proxy;

import java.util.Map;

public class ActiveStreamManager {
    private Map<Long, ServerStream> activeStreamMap;
    private Connection connection;
    private Proxy proxy;

    public void addActiveStream(ServerStream serverStream) {

    }

    public ServerStream findMatchedStream(Long id) {
        return null;
    }
}
