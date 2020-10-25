package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.stream.ServerStreamConnection;
import io.yhheng.superproxy.stream.StreamConnection;

public enum StreamReadFilter implements NetworkReadFilter, ConnectionEventListener {
    INSTANCE;
    public static final String TYPE = "stream";

    @Override
    public FilterStatus onRead(DecodeResult decodeResult, Connection connection) {
        StreamConnection streamConnection = connection.streamConnection();
        // request or response
        streamConnection.dispatch(decodeResult);
        return FilterStatus.STOP;
    }

    @Override
    public FilterStatus onNewConnection(Connection connection) {
        return FilterStatus.CONTINUE;
    }

    @Override
    public FilterStatus onWrite(ByteBuf byteBuf, Connection connection) {
        return null;
    }

    @Override
    public void onEvent(ConnectionEvent event, Connection connection) {
        if (event == ConnectionEvent.Connected) {
            Listener listener = connection.listener();
            // 创建streamConnection,绑定到Connection上
            var serverStreamConnection = new ServerStreamConnection(listener.downstreamProtocol(),
                    connection,
                    listener.proxy());
            connection.setStreamConnection(serverStreamConnection);
        } else if (event == ConnectionEvent.RemoteClose || event == ConnectionEvent.LocalClose) {
            // 清除streamConnection
            StreamConnection streamConnection = connection.streamConnection();
            if (streamConnection != null) {
                streamConnection.close(event == ConnectionEvent.RemoteClose);
            }
        }
    }
}
