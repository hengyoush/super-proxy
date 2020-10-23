package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.protocol.DecodeResult;

public interface NetworkFilter {
    FilterStatus onRead(DecodeResult decodeResult, Connection connection);

    FilterStatus onNewConnection(Connection connection);

    FilterStatus onWrite(ByteBuf byteBuf, Connection connection);
}
