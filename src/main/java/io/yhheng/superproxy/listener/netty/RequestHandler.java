package io.yhheng.superproxy.listener.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.yhheng.superproxy.filter.FilterChain;
import io.yhheng.superproxy.model.Header;
import io.yhheng.superproxy.model.Request;
import io.yhheng.superproxy.network.NettyChannel;

public class RequestHandler extends ChannelDuplexHandler {
    private FilterChain filterChain;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Request) {
            handleRequest(((Request) msg), ctx);
        } else {
            // TODO handle response
        }
    }

    private void handleRequest(Request msg, ChannelHandlerContext ctx) {
        Header header = msg.getHeader();
        if (header.isEvent()) {
            // handle event

        } else {
            NettyChannel nettyChannel = new NettyChannel(ctx.channel());
            filterChain.onRequest(msg, nettyChannel);
        }
    }
}
