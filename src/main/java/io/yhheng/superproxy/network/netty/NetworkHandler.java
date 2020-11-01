package io.yhheng.superproxy.network.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.network.ConnectionEventListener;
import io.yhheng.superproxy.network.FilterStatus;
import io.yhheng.superproxy.network.Listener;
import io.yhheng.superproxy.network.NetworkReadFilter;
import io.yhheng.superproxy.protocol.DecodeResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.yhheng.superproxy.network.ConnectionEventListener.ConnectionEvent.Connected;
import static io.yhheng.superproxy.network.ConnectionEventListener.ConnectionEvent.RemoteClose;

public class NetworkHandler extends ChannelDuplexHandler {
    private final Listener listener;
    private final Map<String, Connection> connTable = new ConcurrentHashMap<>();

    public NetworkHandler(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof DecodeResult) {
            handleData((DecodeResult) msg, connTable.get(ctx.channel().id().asLongText()));
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO new Connection
        Connection connection = null;
        handleNewConnection(connection);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Connection removedConnection = connTable.remove(ctx.channel().id().asLongText());
        removedConnection.close();
        handleCloseConnection(removedConnection);
        ctx.fireChannelInactive();
    }

    public void handleData(DecodeResult decodeResult, Connection connection) {
        List<NetworkReadFilter> networkReadFilters = listener.networkReadFilters();
        for (int i = 0; i < networkReadFilters.size(); i++) {
            FilterStatus filterStatus = networkReadFilters.get(i).onRead(decodeResult, connection);
            if (filterStatus == FilterStatus.STOP) {
                break;
            }
        }
    }

    public void handleNewConnection(Connection connection) {
        List<NetworkReadFilter> networkReadFilters = listener.networkReadFilters();
        List<ConnectionEventListener> connectionEventListeners = listener.connectionEventListeners();
        connTable.put(connection.id(), connection);

        for (var l : connectionEventListeners) {
            l.onEvent(Connected, connection);
        }

        for (int i = 0; i < networkReadFilters.size(); i++) {
            FilterStatus filterStatus = networkReadFilters.get(i).onNewConnection(connection);
            if (filterStatus == FilterStatus.STOP) {
                break;
            }
        }
    }

    public void handleCloseConnection(Connection connection) {
        List<ConnectionEventListener> connectionEventListeners = listener.connectionEventListeners();
        for (var l : connectionEventListeners) {
            l.onEvent(RemoteClose, connection);
        }
    }
}
