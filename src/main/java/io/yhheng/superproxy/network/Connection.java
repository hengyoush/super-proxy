package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.common.utils.Side;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.stream.ServerStreamConnection;

import java.net.InetAddress;

/**
 * 网络连接
 * 目前只表示下游连接
 */
public interface Connection {
    InetAddress remoteAddr();
    InetAddress localAddr();
    void close();
    void write(ByteBuf byteBuf);
    Side side();

    // reference to Listener
    Listener listener();
    ServerStreamConnection streamConnection();

    ServerStreamConnection newStreamConnection(Protocol protocol);
}
