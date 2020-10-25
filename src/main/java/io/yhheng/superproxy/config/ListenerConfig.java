package io.yhheng.superproxy.config;

import java.net.SocketAddress;
import java.util.List;

public class ListenerConfig {
    private String name;
    private SocketAddress address;
    private List<ListenerEventListenerConfig> listenerEventListenerConfigs;
    private List<NetworkReadFilterConfig> networkReadFilterConfigs;
    private List<ConnectionEventListenerConfig> connectionEventListenerConfigs;
    private List<ProxyFilterConfig> proxyFilterConfigs;
    private ProxyConfig proxyConfig;
    private String downstreamProtocolName;

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

    public List<NetworkReadFilterConfig> getNetworkReadFilterConfigs() {
        return networkReadFilterConfigs;
    }

    public void setNetworkReadFilterConfigs(List<NetworkReadFilterConfig> networkReadFilterConfigs) {
        this.networkReadFilterConfigs = networkReadFilterConfigs;
    }

    public String getDownstreamProtocolName() {
        return downstreamProtocolName;
    }

    public void setDownstreamProtocolName(String downstreamProtocolName) {
        this.downstreamProtocolName = downstreamProtocolName;
    }

    public List<ConnectionEventListenerConfig> getConnectionEventListenerConfigs() {
        return connectionEventListenerConfigs;
    }

    public void setConnectionEventListenerConfigs(List<ConnectionEventListenerConfig> connectionEventListenerConfigs) {
        this.connectionEventListenerConfigs = connectionEventListenerConfigs;
    }
}
