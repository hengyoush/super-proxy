package io.yhheng.superproxy.network;

import io.yhheng.superproxy.Server;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.Proxy;

import java.util.List;

public interface Listener {
    void listen();
    void shutdown();
    List<NetworkFilter> networkFilters();
    List<ListenerEventListener> listenerEventListeners();
    Protocol downstreamProtocol();
    Server server();
    Proxy proxy();
}
