package io.yhheng.superproxy.listener.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.yhheng.superproxy.protocol.Decoder.DecodeStatus.COMPLETE;
import static io.yhheng.superproxy.protocol.Decoder.DecodeStatus.ERROR;

public class DecodeHandler extends ByteToMessageDecoder {
    private static final Logger log = LoggerFactory.getLogger(NettyListenerImpl.class);
    private final Protocol protocol;

    public DecodeHandler(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Decoder decoder = protocol.getDecoder();

        do {
            int save = in.readerIndex();
            DecodeResult decodeResult = decoder.decode(in);
            if (decodeResult.getDecodeStatus() != COMPLETE) {
                out.add(decodeResult.getResult());
                continue;
            } else if (decodeResult.getDecodeStatus() == ERROR) {
                log.error("decode downstream request failed!", decodeResult.getException());
            }
            in.readerIndex(save);
            break;
        } while (true);
    }
}
