package io.yhheng.superproxy.network;

import io.yhheng.superproxy.protocol.Header;

public interface ConnectionPool {
    Connection getConnection(Header header);
}
