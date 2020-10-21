package io.yhheng.superproxy.cluster;

import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.network.ConnectionPool;

public interface ClusterManager {
    Cluster getCluster(String name);
    Connection initializeConnectionForHost(Host host);
    public static ClusterManager getInstance() {return null;}
}
