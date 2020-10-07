package io.yhheng.superproxy.proxy;

import io.yhheng.superproxy.protocol.ProtocolConfig;
import io.yhheng.superproxy.proxy.route.RouteConfig;

public class ProxyConfig {
    private ProtocolConfig upstreamProtocolConfig;
    private ProtocolConfig downstreamProtocolConfig;
    private RouteConfig routeConfig; // reference to

    public ProtocolConfig getUpstreamProtocolConfig() {
        return upstreamProtocolConfig;
    }

    public void setUpstreamProtocolConfig(ProtocolConfig upstreamProtocolConfig) {
        this.upstreamProtocolConfig = upstreamProtocolConfig;
    }

    public ProtocolConfig getDownstreamProtocolConfig() {
        return downstreamProtocolConfig;
    }

    public void setDownstreamProtocolConfig(ProtocolConfig downstreamProtocolConfig) {
        this.downstreamProtocolConfig = downstreamProtocolConfig;
    }

    public RouteConfig getRouteConfig() {
        return routeConfig;
    }

    public void setRouteConfig(RouteConfig routeConfig) {
        this.routeConfig = routeConfig;
    }
}
