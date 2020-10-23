package io.yhheng.superproxy.network.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.network.FilterStatus;
import io.yhheng.superproxy.network.Listener;
import io.yhheng.superproxy.network.NetworkFilter;
import io.yhheng.superproxy.protocol.DecodeResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO 修改此处overload方法
 */
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

    public void handleData(DecodeResult decodeResult, Connection connection) {
        List<NetworkFilter> networkFilters = listener.networkFilters();
        for (int i = 0; i < networkFilters.size(); i++) {
            FilterStatus filterStatus = networkFilters.get(i).onRead(decodeResult, connection);
            if (filterStatus == FilterStatus.STOP) {
                break;
            }
        }
    }

    public void handleNewConnection(Connection connection) {
        List<NetworkFilter> networkFilters = listener.networkFilters();
        for (int i = 0; i < networkFilters.size(); i++) {
            FilterStatus filterStatus = networkFilters.get(i).onNewConnection(connection);
            if (filterStatus == FilterStatus.STOP) {
                break;
            }
        }
    }
}
