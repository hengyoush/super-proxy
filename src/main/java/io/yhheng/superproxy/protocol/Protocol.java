package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;

public interface Protocol {
    Decoder getDecoder();

    Encoder getEncoder();

    ByteBuf generateFailedResponse(Header header, String msg);

    static boolean supportHeartBeat(Protocol protocol) {
        return protocol instanceof HeartbeatSupport;
    }
}
