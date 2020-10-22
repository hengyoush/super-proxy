package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface Decoder {
    DecodeResult decode(ByteBuf byteBuf) throws IOException;

    Protocol protocol();

    enum DecodeStatus {
        NEED_MORE_INPUT, COMPLETE, BROKEN
    }
}
