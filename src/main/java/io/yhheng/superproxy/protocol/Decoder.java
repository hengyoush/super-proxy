package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.model.Request;

public interface Decoder {
    DecodeResult decode(ByteBuf byteBuf);

    enum DecodeStatus {
        NEED_MORE_INPUT, COMPLETE, ERROR
    }
}
