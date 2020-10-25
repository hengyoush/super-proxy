package io.yhheng.superproxy.network;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;

public class ConnectionEventListeners extends AbstractObjectRegistry<ConnectionEventListener> {
    public static final ConnectionEventListeners INSTANCE = new ConnectionEventListeners();

    static {
        INSTANCE.register(StreamReadFilter.TYPE, StreamReadFilter.INSTANCE);
    }

    @Override
    protected String desc() {
        return "ConnectionEventListener";
    }
}
