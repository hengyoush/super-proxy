package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.config.RouteConfig;
import io.yhheng.superproxy.protocol.Header;

public interface RouteMatch {
    boolean match(Header header);

    interface Factory {
        RouteMatch create(RouteConfig.RouteMatchConfig config);
    }
}
