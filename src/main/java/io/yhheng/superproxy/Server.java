package io.yhheng.superproxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.network.Listener;
import io.yhheng.superproxy.proxy.route.RouterTable;

import java.util.List;

public class Server {
    private String name;
    private List<Listener> listeners;
    private ClusterManager clusterManager;
    private List<RouterTable>  routerTables;

    public Server() {
    }

    public void startup() {
        listeners.forEach(Listener::listen);
    }

    public void shutdown() {
        listeners.forEach(Listener::shutdown);
    }

    public ClusterManager getClusterManager() {
        return clusterManager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListeners(List<Listener> listeners) {
        this.listeners = listeners;
    }

    public void setClusterManager(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    public void setRouterTables(List<RouterTable> routerTables) {
        this.routerTables = routerTables;
    }
}
