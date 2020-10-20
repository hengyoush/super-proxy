package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;

public class RouteManager extends AbstractObjectRegistry<Routers> {
    public static final RouteManager INSTANCE = new RouteManager();

    @Override
    protected String desc() {
        return "routers";
    }
}
