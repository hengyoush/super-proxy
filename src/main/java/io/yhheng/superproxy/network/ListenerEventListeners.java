package io.yhheng.superproxy.network;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;

public class ListenerEventListeners extends AbstractObjectRegistry<ListenerEventListener> {
    public static final ListenerEventListeners INSTANCE = new ListenerEventListeners();

    private ListenerEventListeners() { super(); }

    @Override
    protected String desc() {
        return "ListenerEventListener";
    }
}
