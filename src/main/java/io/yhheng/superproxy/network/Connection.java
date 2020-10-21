package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.proxy.Proxy;
import io.yhheng.superproxy.stream.StreamConnection;

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

    // reference to Listener
    Listener listener();
    StreamConnection streamConnection();

    // setters
    void setStreamConnection(StreamConnection streamConnection);
}
