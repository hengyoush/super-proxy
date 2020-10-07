package io.yhheng.superproxy.network;

import io.yhheng.superproxy.Server;
import io.yhheng.superproxy.protocol.Protocol;

import java.util.List;

public interface Listener {
    void listen();
    void shutdown();
    List<NetworkFilter> networkFilters();
    Protocol downstreamProtocol();
    Server server();
}
