package io.yhheng.superproxy.config;

import java.util.List;

public class ServerConfig {
    private String name;
    private List<ListenerConfig> listeners;
    private List<RouteConfig> routeConfigs;

    public List<RouteConfig> getRouteConfigs() {
        return routeConfigs;
    }

    public void setRouteConfigs(List<RouteConfig> routeConfigs) {
        this.routeConfigs = routeConfigs;
    }
// TODO log path support

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
}
