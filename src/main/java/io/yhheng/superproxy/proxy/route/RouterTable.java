package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Header;

import java.util.List;

public class RouterTable {
    private String name;
    private List<Route> routes;

    public RouterTable(String name, List<Route> routes) {
        this.name = name;
        this.routes = routes;
        RouteManager.INSTANCE.register(this.name, this);
    }

    public Route match(Header header) {
        for (Route route : routes) {
            if (route.match(header)) {
                return route;
            }
        }
        return null;
    }
}
