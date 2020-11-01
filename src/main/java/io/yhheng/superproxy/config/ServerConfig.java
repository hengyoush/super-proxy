package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;
import io.yhheng.superproxy.Server;
import io.yhheng.superproxy.network.Listener;
import io.yhheng.superproxy.network.ListenerFactory;
import io.yhheng.superproxy.proxy.route.RouterTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerConfig {
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "listeners")
    private List<ListenerConfig> listeners;
    @JSONField(name = "route_tables")
    private List<RouteConfig> routeConfigs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListenerConfig> getListeners() {
        return listeners;
    }

    public void setListeners(List<ListenerConfig> listeners) {
        this.listeners = listeners;
    }

    public List<RouteConfig> getRouteConfigs() {
        return routeConfigs;
    }

    public void setRouteConfigs(List<RouteConfig> routeConfigs) {
        this.routeConfigs = routeConfigs;
    }

    Server make() {
        // 初始化Listener
        List<ListenerConfig> listenerConfigs = this.getListeners();
        List<RouterTable> routerTables = routeConfigs.stream().map(RouteConfig::make).collect(Collectors.toList());
        List<Listener> listeners = listenerConfigs.stream().map(ListenerFactory::createListener).collect(Collectors.toCollection(ArrayList::new));

        Server server = new Server();
        server.setListeners(listeners);
        server.setName(name);
        server.setRouterTables(routerTables);
        return server;
    }
}
