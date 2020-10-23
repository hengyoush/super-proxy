package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;

public interface Encoder {
    ByteBuf encode(Header header, ByteBuf body);
}
