package io.yhheng.superproxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.config.ServerConfig;
import io.yhheng.superproxy.network.Listener;

import java.util.List;

public class Server {
    private String name;
    private List<Listener> listeners;
    private ClusterManager clusterManager;

    public Server(ServerConfig serverConfig) {

    }

    public ClusterManager getClusterManager() {
        return clusterManager;

    }
}
