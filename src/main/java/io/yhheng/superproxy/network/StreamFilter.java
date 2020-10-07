package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.proxy.Proxy;
import io.yhheng.superproxy.stream.ServerStreamConnection;

public class StreamFilter implements NetworkFilter {
    @Override
    public FilterStatus onRead(ByteBuf byteBuf, Connection connection) {
        Proxy proxy = connection.proxy();
        if (proxy.getServerStreamConnection() == null) {
            proxy.newServerStreamConnection(connection);
        }
        ServerStreamConnection serverStreamConnection = proxy.getServerStreamConnection();
        // request or response
        serverStreamConnection.dispatch(byteBuf);
        return FilterStatus.STOP;
    }

    @Override
    public void onNewConnection(Connection connection) {
        Listener listener = connection.listener();
        // 创建proxy， 绑定到connection上
        Proxy proxy = new Proxy();
        proxy.setDownstreamProtocol(listener.downstreamProtocol());
        proxy.setServerConnection(connection);
        proxy.setClusterManager(listener.server().getClusterManager());
        connection.setProxy(proxy);
    }

    @Override
    public FilterStatus onWrite(ByteBuf byteBuf, Connection connection) {
        return null;
    }
}
