package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.proxy.Proxy;

import java.util.Map;

public class ActiveStreamManager {
    private Map<Long, Downstream> activeStreamMap;
    private Connection connection;
    private Proxy proxy;

    public void addActiveStream(Downstream downstream) {

    }

    public Downstream findMatchedStream(Long id) {
        return null;
    }
}
