package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Header;

import java.util.List;

public class VirtualHostImpl implements VirtualHost {
    private String logicName;
    private List<Route> routes;

    @Override
    public Route getRoute(Header header) {
        for (Route route : routes) {
            if (route.match(header)) {
                return route;
            }
        }
        return null;
    }
}
