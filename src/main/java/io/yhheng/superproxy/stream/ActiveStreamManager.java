package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.proxy.Proxy;

import java.util.Map;

public class ActiveStreamManager {
    private Map<Long, ServerStream> activeServerStreamMap;
    private Map<Long, ClientStream> activeClientStreamMap;
    private Connection connection;
    private Proxy proxy;

    public void addServerStream(ServerStream serverStream) {

    }

    public void addClientStream(ClientStream clientStream) {

    }

    public ServerStream findMatchedServerStream(Long id) {
        return null;
    }

    public ClientStream findMatchClientStream(Long streamId) {
        return null;
    }
}
