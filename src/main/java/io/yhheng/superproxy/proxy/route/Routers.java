package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Header;

import java.util.Map;

public class Routers {
    private Map<String, VirtualHost> virtualHosts;

    public Route match(Header header) {
        String host = header.host();
        VirtualHost virtualHost = virtualHosts.get(host);
        if (virtualHost == null) {
            // TODO no VirtualHost found
            return null;
        }

        Route route = virtualHost.getRoute(header);
        if (route == null) {
            // TODO no route rule matched
            return null;
        }
        return route;
    }
}
