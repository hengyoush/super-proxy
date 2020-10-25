package io.yhheng.superproxy.cluster;

import java.util.List;
// TODO health check
public interface Cluster {
    List<Host> hosts();
    Host selectHost();
}
