package io.yhheng.superproxy.cluster;

import java.util.List;

public interface Cluster {
    List<Host> hosts();
    Host selectHost();
}
