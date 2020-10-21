package io.yhheng.superproxy.config;

import java.util.List;

public class ProxyConfig {
    private String routerConfigName;
    private List<ProxyFilterConfig> proxyFilterConfigs;

    public String getRouterConfigName() {
        return routerConfigName;
    }

    public void setRouterConfigName(String routerConfigName) {
        this.routerConfigName = routerConfigName;
    }

    public List<ProxyFilterConfig> getProxyFilterConfigs() {
        return proxyFilterConfigs;
    }

    public void setProxyFilterConfigs(List<ProxyFilterConfig> proxyFilterConfigs) {
        this.proxyFilterConfigs = proxyFilterConfigs;
    }
}
