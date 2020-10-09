package io.yhheng.superproxy.stream;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.protocol.Protocol;

public interface StreamConnection {
    void dispatch(ByteBuf byteBuf);
    Protocol protocol();
}
