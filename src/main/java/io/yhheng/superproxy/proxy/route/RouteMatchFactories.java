package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;
import io.yhheng.superproxy.proxy.route.dubbo.DubboRouteMatch;

public class RouteMatchFactories extends AbstractObjectRegistry<RouteMatch.Factory> {
    public static final RouteMatchFactories INSTANCE = new RouteMatchFactories();

    static {
        INSTANCE.register(DubboRouteMatch.FactoryImpl.TYPE, new DubboRouteMatch.FactoryImpl());
    }

    @Override
    protected String desc() {
        return "RouteMatchFactory";
    }
}
