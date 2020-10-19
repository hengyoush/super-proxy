package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Header;

import java.util.List;

public class Routers {
    private List<Route> routes;

    public Route match(Header header) {
        for (Route route : routes) {
            if (route.match(header)) {
                return route;
            }
        }
        return null;
    }
}
