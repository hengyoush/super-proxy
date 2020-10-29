package io.yhheng.superproxy.network;

import io.yhheng.superproxy.common.utils.Side;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.stream.ClientStreamConnection;

public interface ClientConnection extends Connection {
    @Override
    default Side side() {
        return Side.CONSUMER;
    }

    @Override
    ClientStreamConnection newStreamConnection(Protocol protocol);

    @Override
    ClientStreamConnection streamConnection();
}
