package io.yhheng.superproxy.cluster;

import io.yhheng.superproxy.network.ConnectionPool;

public interface Host {
    Cluster cluster();
    ConnectionPool getConnPool();
}
