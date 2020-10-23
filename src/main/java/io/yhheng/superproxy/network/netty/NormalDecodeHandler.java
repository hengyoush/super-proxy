package io.yhheng.superproxy.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Protocol;

import java.io.IOException;
import java.util.List;

public class NormalDecodeHandler extends ByteToMessageDecoder {
    private Protocol protocol;

    public NormalDecodeHandler(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        DecodeResult decodeResult = protocol.getDecoder().decode(in);
        while (in.isReadable()) {
            int savedIndex = in.readerIndex();
            if (decodeResult.getDecodeStatus() == Decoder.DecodeStatus.NEED_MORE_INPUT) {
                in.readerIndex(savedIndex);
                break;
            } else if (decodeResult.getDecodeStatus() == Decoder.DecodeStatus.COMPLETE) {
                if (savedIndex == in.readerIndex()) {
                    throw new IOException("Decode without read data.");
                }
                out.add(decodeResult);
            }
        }
    }
}
