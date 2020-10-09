package io.yhheng.superproxy.stream;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.Protocol;

public class ClientStreamConnection implements StreamConnection {
    private Protocol protocol;
    private Connection connection;

    @Override
    public void dispatch(ByteBuf byteBuf) {

    }

    @Override
    public Protocol protocol() {
        return protocol;
    }

    public ClientStream newStream() {
        return null;
    }
}
