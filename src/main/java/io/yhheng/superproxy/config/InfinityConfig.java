package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;
import io.yhheng.superproxy.Infinity;
import io.yhheng.superproxy.Server;
import io.yhheng.superproxy.announce.Announcer;
import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.common.utils.Validate;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public class InfinityConfig {
    @JSONField(name = "servers")
    private List<ServerConfig> serverConfigs;
    @JSONField(name = "cluster_manager")
    private ClusterManagerConfig clusterManagerConfig;
    @JSONField(name = "announcers")
    private List<AnnouncerConfig> announcerConfigs;

    public List<ServerConfig> getServerConfigs() {
        return serverConfigs;
    }

    public void setServerConfigs(List<ServerConfig> serverConfigs) {
        this.serverConfigs = serverConfigs;
    }

    public ClusterManagerConfig getClusterManagerConfig() {
        return clusterManagerConfig;
    }

    public void setClusterManagerConfig(ClusterManagerConfig clusterManagerConfig) {
        this.clusterManagerConfig = clusterManagerConfig;
    }

    public Infinity make() {
        Validate.assertNotEmpty(serverConfigs, "ServerConfig");
        Validate.assertNotNull(clusterManagerConfig, "clusterManagerConfig");

        List<Server> servers = serverConfigs.stream().map(ServerConfig::make).collect(toUnmodifiableList());
        ClusterManager clusterManager = clusterManagerConfig.make();
        List<Announcer> announcers = announcerConfigs.stream().map(AnnouncerConfig::make).collect(toUnmodifiableList());
        return new Infinity(servers, clusterManager, announcers);
    }
}
