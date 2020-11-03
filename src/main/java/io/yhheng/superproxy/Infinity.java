package io.yhheng.superproxy;

import com.alibaba.fastjson.JSONObject;
import io.yhheng.superproxy.announce.Announcer;
import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.config.InfinityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Infinity {
    private static final Logger log = LoggerFactory.getLogger(Infinity.class);

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
        log.info("Infinity开始启动");
        servers.forEach(Server::startup);
        announcers.forEach(Announcer::start);
        log.info("Infinity启动成功");
    }

    public void shutdown() {
        servers.forEach(Server::shutdown);
        announcers.forEach(Announcer::shutdown);
    }
}
