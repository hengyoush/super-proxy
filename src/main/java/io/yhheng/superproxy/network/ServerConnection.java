package io.yhheng.superproxy.network;

import io.yhheng.superproxy.common.utils.Side;

public interface ServerConnection extends Connection {
    @Override
    default Side side() {
        return Side.PROVIDER;
    }
}
