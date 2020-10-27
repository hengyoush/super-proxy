package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

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
}
