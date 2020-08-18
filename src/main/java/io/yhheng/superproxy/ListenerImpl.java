package io.yhheng.superproxy;

import io.yhheng.superproxy.config.ListenerConfig;
import io.yhheng.superproxy.filter.FilterChain;

public abstract class ListenerImpl {
    private ListenerConfig listenerConfig;
    private FilterChain filterChain;

    public abstract void startup();
    public abstract void shutdown();
}
