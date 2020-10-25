package io.yhheng.superproxy.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
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
        // 我们可以确定一定可以解码成功
        DecodeResult decodeResult = protocol.getDecoder().decode(decode);
        assert decodeResult.getDecodeStatus() == Decoder.DecodeStatus.COMPLETE;
        return decodeResult;
    }
}
