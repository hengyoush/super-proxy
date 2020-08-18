package io.yhheng.superproxy;

import java.util.List;

public class SuperProxyServer implements Server {
    private List<ListenerImpl> listeners;
    private ClusterManager clusterManager;

    @Override
    public void startup() {
        listeners.forEach(ListenerImpl::startup);
    }

    @Override
    public void shutdown() {

    }
}
