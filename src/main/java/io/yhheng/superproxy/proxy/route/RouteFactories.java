package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;
import io.yhheng.superproxy.proxy.route.dubbo.DubboRouteFactory;

public class RouteFactories extends AbstractObjectRegistry<Route.Factory> {
    public static final RouteFactories INSTANCE = new RouteFactories();

    static {
        INSTANCE.register(DubboRouteFactory.TYPE, DubboRouteFactory.INSTANCE);
    }

    @Override
    protected String desc() {
        return "RouteFactory";
    }
}
