package io.yhheng.superproxy.network;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;

public class ActiveListeners extends AbstractObjectRegistry<Listener> {
    public static final ActiveListeners INSTANCE = new ActiveListeners();

    @Override
    protected String desc() {
        return "ActiveListener";
    }
}
