package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.model.Request;
import io.yhheng.superproxy.model.Packet;

public interface Protocol {
    Decoder getDecoder();
}
