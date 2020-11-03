package io.yhheng.superproxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.network.Listener;
import io.yhheng.superproxy.proxy.route.RouterTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private String name;
    private List<Listener> listeners;
    private ClusterManager clusterManager;
    private List<RouterTable>  routerTables;

    public Server() {
    }

    public void startup() {
        log.info("Server: {} 正在启动...", name);
        listeners.forEach(Listener::listen);
        log.info("Server: {} 启动成功.", name);
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
