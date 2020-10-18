package io.yhheng.superproxy.config;

public class ProxyConfig {
    private String downstreamProtocol;
    private String upstreamProtocol;
    private String routerConfigName;

    public String getDownstreamProtocol() {
        return downstreamProtocol;
    }

    public void setDownstreamProtocol(String downstreamProtocol) {
        this.downstreamProtocol = downstreamProtocol;
    }

    public String getUpstreamProtocol() {
        return upstreamProtocol;
    }

    public void setUpstreamProtocol(String upstreamProtocol) {
        this.upstreamProtocol = upstreamProtocol;
    }

    public String getRouterConfigName() {
        return routerConfigName;
    }

    public void setRouterConfigName(String routerConfigName) {
        this.routerConfigName = routerConfigName;
    }
}
