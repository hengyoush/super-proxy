package io.yhheng.superproxy.network;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;

public class NetworkReadFilters extends AbstractObjectRegistry<NetworkReadFilter> {
    public static final NetworkReadFilters INSTANCE = new NetworkReadFilters();

    static {
        INSTANCE.register(StreamReadFilter.TYPE, StreamReadFilter.INSTANCE);
    }

    @Override
    protected String desc() {
        return "NetworkReadFilter";
    }
}
