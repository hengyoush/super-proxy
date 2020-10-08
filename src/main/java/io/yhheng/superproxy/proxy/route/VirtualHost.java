package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Header;

public interface VirtualHost {
    Route getRoute(Header header);
}
