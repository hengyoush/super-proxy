package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Header;

public abstract class AbstractRoute implements Route {
    protected RouteMatch routeMatch;
    protected RouteAction routeAction;

    public AbstractRoute(RouteMatch routeMatch, RouteAction routeAction) {
        this.routeMatch = routeMatch;
        this.routeAction = routeAction;
    }

    @Override
    public boolean match(Header header) {
        return routerMatch().match(header);
    }

    @Override
    public RouteMatch routerMatch() {
        return this.routeMatch;
    }

    @Override
    public RouteAction routerAction() {
        return this.routeAction;
    }
}
