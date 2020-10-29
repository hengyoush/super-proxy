package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.config.RouteConfig;
import io.yhheng.superproxy.config.RouteConfig.RouteEntry;
import io.yhheng.superproxy.protocol.Header;

public interface Route {
    boolean match(Header header);

    RouteMatch routerMatch();
    RouteAction routerAction();

    interface Factory {
        Route create(RouteEntry routeEntry);
        RouteMatch createRouteMatch(RouteConfig.RouteMatchConfig routeMatchConfig);
        RouteAction createRouteAction(RouteConfig.RouteActionConfig routeActionConfig);
    }
}
