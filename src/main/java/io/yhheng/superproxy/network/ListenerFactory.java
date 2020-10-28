package io.yhheng.superproxy.network;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.config.ConnectionEventListenerConfig;
import io.yhheng.superproxy.config.ListenerConfig;
import io.yhheng.superproxy.config.ListenerEventListenerConfig;
import io.yhheng.superproxy.config.NetworkReadFilterConfig;
import io.yhheng.superproxy.network.netty.NettyListenerImpl;
import io.yhheng.superproxy.protocol.Protocols;
import io.yhheng.superproxy.proxy.Proxy;
import io.yhheng.superproxy.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ListenerFactory {
    private static final Logger log = LoggerFactory.getLogger(ListenerFactory.class);

    public static Listener createListener(ListenerConfig listenerConfig) {
        var listenerEventListenerConfigs = listenerConfig.getListenerEventListenerConfigs();

        var listenerEventListeners = new ArrayList<ListenerEventListener>();
        for (ListenerEventListenerConfig config : listenerEventListenerConfigs) {
            var l = ListenerEventListeners.INSTANCE.get(config.getType());
            if (l == null) {
                log.warn("type :{}的listenerEventListener不存在!", config.getType());
            } else {
                listenerEventListeners.add(l);
            }
        }

        List<NetworkReadFilterConfig> networkReadFilterConfigs = listenerConfig.getNetworkReadFilterConfigs();
        var networkFilters = new ArrayList<NetworkReadFilter>();
        for (var config : networkReadFilterConfigs) {
            NetworkReadFilter f = NetworkReadFilters.INSTANCE.get(config.getType());
            if (f == null) {
                log.warn("type :{}的networkFilter不存在!", config.getType());
            } else {
                networkFilters.add(f);
            }
        }

        List<ConnectionEventListenerConfig> connectionEventListenerConfigs = listenerConfig.getConnectionEventListenerConfigs();
        var connectionEventListeners = new ArrayList<ConnectionEventListener>();
        for (var config : connectionEventListenerConfigs) {
            ConnectionEventListener l = ConnectionEventListeners.INSTANCE.get(config.getType());
            if (l == null) {
                log.warn("type :{}的ConnectionEventListenerConfig不存在!", config.getType());
            } else {
                connectionEventListeners.add(l);
            }
        }

        Proxy proxy = ProxyFactory.createProxy(listenerConfig.getProxyConfig(), ClusterManager.getInstance());

        return new NettyListenerImpl(listenerConfig.getName(),
                new InetSocketAddress(listenerConfig.getAddress(), listenerConfig.getPort()),
                networkFilters,
                listenerEventListeners,
                connectionEventListeners,
                Protocols.INSTANCE.get(listenerConfig.getDownstreamProtocolName()),
                proxy);
    }
}
