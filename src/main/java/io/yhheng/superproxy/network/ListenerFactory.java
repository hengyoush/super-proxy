package io.yhheng.superproxy.network;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.config.ListenerConfig;
import io.yhheng.superproxy.config.ListenerEventListenerConfig;
import io.yhheng.superproxy.config.NetworkFilterConfig;
import io.yhheng.superproxy.network.netty.NettyListenerImpl;
import io.yhheng.superproxy.protocol.Protocols;
import io.yhheng.superproxy.proxy.Proxy;
import io.yhheng.superproxy.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        List<NetworkFilterConfig> networkFilterConfigs = listenerConfig.getNetworkFilterConfigs();
        var networkFilters = new ArrayList<NetworkFilter>();
        for (var config : networkFilterConfigs) {
            NetworkFilter f = NetworkFilters.INSTANCE.get(config.getType());
            if (f == null) {
                log.warn("type :{}的networkFilter不存在!", config.getType());
            } else {
                networkFilters.add(f);
            }
        }

        Proxy proxy = ProxyFactory.createProxy(listenerConfig.getProxyConfig(), ClusterManager.getInstance());

        return new NettyListenerImpl(listenerConfig.getName(),
                listenerConfig.getAddress(),
                networkFilters,
                listenerEventListeners,
                Protocols.INSTANCE.get(listenerConfig.getDownstreamProtocolName()),
                proxy);
    }
}
