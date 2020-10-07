package io.yhheng.superproxy.network;

import io.yhheng.superproxy.proxy.filter.FilterConfig;
import io.yhheng.superproxy.proxy.route.RouteConfig;

public class ListenerConfig {
    private String type;
    private String name;
    private String address;
    private Integer port;

    // reference to
    private RouteConfig routeConfig;
    private FilterConfig filterConfig;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public RouteConfig getRouteConfig() {
        return routeConfig;
    }

    public void setRouteConfig(RouteConfig routeConfig) {
        this.routeConfig = routeConfig;
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
