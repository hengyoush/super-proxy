package io.yhheng.superproxy.config;

import java.net.SocketAddress;
import java.util.List;

public class ListenerConfig {
    private String name;
    private SocketAddress address;
    private List<ListenerEventListenerConfig> listenerEventListenerConfigs;
    private List<NetworkFilterConfig> networkFilterConfigs;
    private List<ProxyFilterConfig> proxyFilterConfigs;
    private ProxyConfig proxyConfig;

    // TODO ConnectionIdleTimeout


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    public List<ListenerEventListenerConfig> getListenerEventListenerConfigs() {
        return listenerEventListenerConfigs;
    }

    public void setListenerEventListenerConfigs(List<ListenerEventListenerConfig> listenerEventListenerConfigs) {
        this.listenerEventListenerConfigs = listenerEventListenerConfigs;
    }

    public List<ProxyFilterConfig> getProxyFilterConfigs() {
        return proxyFilterConfigs;
    }

    public void setProxyFilterConfigs(List<ProxyFilterConfig> proxyFilterConfigs) {
        this.proxyFilterConfigs = proxyFilterConfigs;
    }

    public ProxyConfig getProxyConfig() {
        return proxyConfig;
    }

    public void setProxyConfig(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    public List<NetworkFilterConfig> getNetworkFilterConfigs() {
        return networkFilterConfigs;
    }

    public void setNetworkFilterConfigs(List<NetworkFilterConfig> networkFilterConfigs) {
        this.networkFilterConfigs = networkFilterConfigs;
    }
}
