package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO 修改此处overload方法
 */
public class NetworkHandler extends ByteToMessageDecoder {
    private final Listener listener;
    private final Map<String, Connection> connTable = new ConcurrentHashMap<>();

    public NetworkHandler(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 通过ctx拿到Connection
        String channelId = ctx.channel().id().asLongText();
        Connection connection = connTable.get(channelId);
        if (connection == null) {
            // TODO log warn here
        }

        handleData(in, connection);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO new Connection
        Connection connection = null;
        handleNewConnection(connection);
    }

    public void handleData(ByteBuf byteBuf, Connection connection) {
        List<NetworkFilter> networkFilters = listener.networkFilters();
        for (int i = 0; i < networkFilters.size(); i++) {
            FilterStatus filterStatus = networkFilters.get(i).onRead(byteBuf, connection);
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
