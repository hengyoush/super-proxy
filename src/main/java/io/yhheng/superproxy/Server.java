package io.yhheng.superproxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.network.Listener;

import java.util.List;

public class Server {
    private String name;
    private List<Listener> listeners;
    private ClusterManager clusterManager;

    public ClusterManager getClusterManager() {
        return clusterManager;
    }
}
