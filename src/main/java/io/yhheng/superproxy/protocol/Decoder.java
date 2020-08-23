package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.model.Request;

import java.io.IOException;

public interface Decoder {
    DecodeResult decode(ByteBuf byteBuf) throws IOException;

    enum DecodeStatus {
        NEED_MORE_INPUT, COMPLETE, ERROR
    }
}
