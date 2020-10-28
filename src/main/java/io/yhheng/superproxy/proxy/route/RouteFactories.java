package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;

public class RouteFactories extends AbstractObjectRegistry<Route.Factory> {
    public static final RouteFactories INSTANCE = new RouteFactories();

    static {

    }

    @Override
    protected String desc() {
        return "RouteFactory";
    }
}
