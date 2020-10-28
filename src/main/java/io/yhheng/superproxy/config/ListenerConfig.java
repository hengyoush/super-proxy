package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;

import java.net.SocketAddress;
import java.util.List;

public class ListenerConfig {
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "port")
    private int port;
    @JSONField(name = "type")
    private String type; // TCP or UDP, currently only support TCP
    @JSONField(name = "listener_event_listeners")
    private List<ListenerEventListenerConfig> listenerEventListenerConfigs;
    @JSONField(name = "network_filters")
    private List<NetworkReadFilterConfig> networkReadFilterConfigs;
    @JSONField(name = "connection_event_listeners")
    private List<ConnectionEventListenerConfig> connectionEventListenerConfigs;
    @JSONField(name = "downstream_protocol")
    private ProtocolConfig downstreamProtocol;
    @JSONField(name = "proxy_config")
    private ProxyConfig proxyConfig;
    private String downstreamProtocolName;

    // TODO ConnectionIdleTimeout


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListenerEventListenerConfig> getListenerEventListenerConfigs() {
        return listenerEventListenerConfigs;
    }

    public void setListenerEventListenerConfigs(List<ListenerEventListenerConfig> listenerEventListenerConfigs) {
        this.listenerEventListenerConfigs = listenerEventListenerConfigs;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
