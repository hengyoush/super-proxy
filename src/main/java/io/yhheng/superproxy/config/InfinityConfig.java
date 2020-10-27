package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class InfinityConfig {
    @JSONField(name = "servers")
    private List<ServerConfig> serverConfigs;
    @JSONField(name = "cluster_manager")
    private List<ClusterManagerConfig> clusterConfigs;

    public List<ServerConfig> getServerConfigs() {
        return serverConfigs;
    }

    public void setServerConfigs(List<ServerConfig> serverConfigs) {
        this.serverConfigs = serverConfigs;
    }

    public List<ClusterManagerConfig> getClusterConfigs() {
        return clusterConfigs;
    }

    public void setClusterConfigs(List<ClusterManagerConfig> clusterConfigs) {
        this.clusterConfigs = clusterConfigs;
    }
}
