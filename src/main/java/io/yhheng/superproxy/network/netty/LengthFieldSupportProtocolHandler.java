package io.yhheng.superproxy.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.yhheng.superproxy.protocol.LengthFieldSupportProtocol;

public class LengthFieldSupportProtocolHandler extends LengthFieldBasedFrameDecoder {
    private LengthFieldSupportProtocol protocol;

    public LengthFieldSupportProtocolHandler(LengthFieldSupportProtocol protocol) {
        super(protocol.maxFrameLength(), protocol.frameLengthFieldOffset(), protocol.frameLengthFieldLength());
        this.protocol = protocol;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
        return protocol.getDecoder().decode(decode);
    }
}
