package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.network.Connection;

import java.util.Collection;
import java.util.Map;

public class ActiveStreamManager {
    private Map<Long, ServerStream> activeServerStreamMap;
    private Map<Long, ClientStream> activeClientStreamMap;
    private Connection connection;

    public void addServerStream(ServerStream serverStream) {

    }

    public void addClientStream(ClientStream clientStream) {

    }

    public void removeStream(Long streamId) {
        // remove server and client
    }

    public ServerStream findMatchedServerStream(Long id) {
        return null;
    }

    public ClientStream findMatchClientStream(Long streamId) {
        return null;
    }

    public Collection<ClientStream> getAllClientStreams() {
        return activeClientStreamMap.values();
    }

    public Collection<ServerStream> getAllServerStreams() {
        return activeServerStreamMap.values();
    }

    public void shutdown() {
        activeClientStreamMap.clear();
        activeServerStreamMap.clear();
    }
}
