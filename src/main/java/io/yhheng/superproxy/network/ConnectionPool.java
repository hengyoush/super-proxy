package io.yhheng.superproxy.network;

import io.yhheng.superproxy.protocol.Header;

public interface ConnectionPool {
    ClientConnection getConnection(Header header);
}
