package io.yhheng.superproxy.proxy.route.dubbo;

import io.yhheng.superproxy.proxy.route.AbstractRoute;
import io.yhheng.superproxy.proxy.route.RouteAction;
import io.yhheng.superproxy.proxy.route.RouteMatch;

public class DubboRoute extends AbstractRoute {

    public DubboRoute(RouteMatch routeMatch, RouteAction routeAction) {
        super(routeMatch, routeAction);
    }
}
