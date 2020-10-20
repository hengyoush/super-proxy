package io.yhheng.superproxy.network;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;

public class NetworkFilters extends AbstractObjectRegistry<NetworkFilter> {
    public static final NetworkFilters INSTANCE = new NetworkFilters();

    @Override
    protected String desc() {
        return "NetworkFilter";
    }
}