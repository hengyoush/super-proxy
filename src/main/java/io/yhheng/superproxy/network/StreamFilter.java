package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.stream.ServerStreamConnection;
import io.yhheng.superproxy.stream.StreamConnection;

public class StreamFilter implements NetworkFilter {
    public static final String TYPE = "stream";

    @Override
    public FilterStatus onRead(ByteBuf byteBuf, Connection connection) {
        StreamConnection streamConnection = connection.streamConnection();
        // request or response
        streamConnection.dispatch(byteBuf);
        return FilterStatus.STOP;
    }

    @Override
    public void onNewConnection(Connection connection) {
        Listener listener = connection.listener();
        // 创建streamConnection,绑定到Connection上
        var serverStreamConnection = new ServerStreamConnection(listener.downstreamProtocol(),
                connection,
                listener.proxy());
        connection.setStreamConnection(serverStreamConnection);
    }

    @Override
    public FilterStatus onWrite(ByteBuf byteBuf, Connection connection) {
        return null;
    }
}
