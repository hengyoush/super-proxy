package io.yhheng.superproxy;

import io.yhheng.superproxy.config.ListenerConfig;
import io.yhheng.superproxy.filter.FilterChain;
import io.yhheng.superproxy.protocol.Protocol;

public abstract class ListenerImpl {
    protected ListenerConfig listenerConfig;
    protected FilterChain filterChain;
    protected Protocol protocol;

    public abstract void startup();
    public abstract void shutdown();
}
