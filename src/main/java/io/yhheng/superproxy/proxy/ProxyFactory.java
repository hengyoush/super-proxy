package io.yhheng.superproxy.proxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.config.ProxyConfig;
import io.yhheng.superproxy.proxy.filter.ProxyFilters;
import io.yhheng.superproxy.proxy.route.RouteManager;

public class ProxyFactory {
    public static Proxy createProxy(ProxyConfig proxyConfig, ClusterManager clusterManager) {
        return new Proxy(RouteManager.INSTANCE.get(proxyConfig.getRouterConfigName()), clusterManager, ProxyFilters.INSTANCE);
    }
}
