package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Header;

public interface RouteMatch {
    boolean match(Header header);
}
