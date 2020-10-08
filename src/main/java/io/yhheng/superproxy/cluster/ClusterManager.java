package io.yhheng.superproxy.cluster;

import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.network.ConnectionPool;

public interface ClusterManager {
    Cluster getCluster(String name);
    Connection connForHost(Host host);
}
