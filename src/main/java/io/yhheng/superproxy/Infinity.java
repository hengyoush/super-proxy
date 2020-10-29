package io.yhheng.superproxy;

import com.alibaba.fastjson.JSONObject;
import io.yhheng.superproxy.announce.Announcer;
import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.config.InfinityConfig;

import java.util.List;

public class Infinity {
    private List<Server> servers;
    private ClusterManager clusterManager;
    private List<Announcer> announcers;

    public static Infinity parse(String json) {
        return JSONObject.parseObject(json, InfinityConfig.class).make();
    }

    public Infinity(List<Server> servers, ClusterManager clusterManager, List<Announcer> announcers) {
        this.servers = servers;
        this.clusterManager = clusterManager;
        this.announcers = announcers;
    }

    public void startup() {
        servers.forEach(Server::startup);
        announcers.forEach(Announcer::announceAll);
    }

    public void shutdown() {
        servers.forEach(Server::shutdown);
        announcers.forEach(Announcer::unAnnounce);
    }
}
