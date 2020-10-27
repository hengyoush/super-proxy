package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ProxyConfig {
    @JSONField(name = "route_config_name")
    private String routerConfigName;
    @JSONField(name = "proxy_filters")
    private List<ProxyFilterConfig> proxyFilterConfigs;
    @JSONField(name = "retry_config")
    private RetryConfig retryConfig;

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

    public RetryConfig getRetryConfig() {
        return retryConfig;
    }

    public void setRetryConfig(RetryConfig retryConfig) {
        this.retryConfig = retryConfig;
    }
}
