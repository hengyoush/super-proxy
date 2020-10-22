package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;

public interface NetworkFilter {
    FilterStatus onRead(ByteBuf byteBuf, Connection connection);

    FilterStatus onNewConnection(Connection connection);

    FilterStatus onWrite(ByteBuf byteBuf, Connection connection);
}
