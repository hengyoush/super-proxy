package io.yhheng.superproxy.network.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class NormalDecodeHandler extends ByteToMessageDecoder {
    private static final Logger log = LoggerFactory.getLogger(NormalDecodeHandler.class);
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
                log.debug("[NormalDecodeHandler] 解码网络请求需要更多数据");
                in.readerIndex(savedIndex);
                break;
            } else if (decodeResult.getDecodeStatus() == Decoder.DecodeStatus.COMPLETE) {
                if (savedIndex == in.readerIndex()) {
                    throw new IOException("Decode without read data.");
                }
                if (log.isDebugEnabled()) {
                    log.debug("[NormalDecodeHandler] 解码网络请求完成,请求:{}", JSON.toJSONString(decodeResult));
                }
                out.add(decodeResult);
            }
        }
    }
}
