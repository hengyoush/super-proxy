package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.stream.ServerStreamConnection;

public enum StreamReadFilter implements NetworkReadFilter, ConnectionEventListener {
    INSTANCE;
    public static final String TYPE = "stream";

    @Override
    public FilterStatus onRead(DecodeResult decodeResult, Connection connection) {
        ServerStreamConnection serverStreamConnection = connection.streamConnection();
        // request or response
        serverStreamConnection.dispatch(decodeResult);
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
//            var streamConnection = new DuplexStreamConnectionImpl(listener.downstreamProtocol(),
//                    connection,
//                    listener.proxy());
            connection.newStreamConnection(listener.downstreamProtocol());
        } else if (event == ConnectionEvent.RemoteClose || event == ConnectionEvent.LocalClose) {
            // 清除streamConnection
            ServerStreamConnection serverStreamConnection = connection.streamConnection();
            if (serverStreamConnection != null) {
                serverStreamConnection.close(event == ConnectionEvent.RemoteClose);
            }
        }
    }
}
