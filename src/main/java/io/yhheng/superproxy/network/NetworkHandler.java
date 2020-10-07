package io.yhheng.superproxy.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * TODO 修改此处overload方法
 */
public class NetworkHandler extends ByteToMessageDecoder {
    private final Listener listener;

    public NetworkHandler(Listener listener) {
        this.listener = listener;
    }

    public void handle(ByteBuf byteBuf, Connection connection) {
        List<NetworkFilter> networkFilters = listener.networkFilters();
        for (int i = 0; i < networkFilters.size(); i++) {
            FilterStatus filterStatus = networkFilters.get(i).onRead(byteBuf, connection);
            if (filterStatus == FilterStatus.STOP) {
                break;
            }
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }
}
