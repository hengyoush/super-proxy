package io.yhheng.superproxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.config.ListenerConfig;
import io.yhheng.superproxy.config.ServerConfig;
import io.yhheng.superproxy.network.Listener;
import io.yhheng.superproxy.network.ListenerFactory;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private String name;
    private List<Listener> listeners;
    private ClusterManager clusterManager;

    public Server(ServerConfig serverConfig) {
        this.name = serverConfig.getName();
        listeners = new ArrayList<>();
        // 初始化Listener
        List<ListenerConfig> listenerConfigs = serverConfig.getListeners();
        for (ListenerConfig lc : listenerConfigs) {
            listeners.add(ListenerFactory.createListener(lc));
        }

        // 启动listener
        listeners.forEach(Listener::listen);
    }

    public ClusterManager getClusterManager() {
        return clusterManager;

    }
}
