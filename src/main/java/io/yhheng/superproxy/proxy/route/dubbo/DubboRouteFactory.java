package io.yhheng.superproxy.proxy.route.dubbo;

import io.yhheng.superproxy.config.RouteConfig;
import io.yhheng.superproxy.proxy.route.Route;
import io.yhheng.superproxy.proxy.route.RouteMatch;
import io.yhheng.superproxy.proxy.route.RouteMatchFactories;

import java.util.Objects;

public class DubboRouteFactory implements DubboRoute.Factory {

    public static final String TYPE = DubboConstants.PROTOCOL_NAME;

    @Override
    public Route create(RouteConfig.RouteEntry routeEntry) {
        String type = routeEntry.getType();
        assert Objects.equals(type, TYPE);
        RouteConfig.RouteMatchConfig routeMatchConfig = routeEntry.getRouteMatchConfig();
        RouteMatch routeMatch = RouteMatchFactories.INSTANCE.get(type).create(routeMatchConfig);
        new DubboRoute(routeMatch, )

        return null;
    }
}
