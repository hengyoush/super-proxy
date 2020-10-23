package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;

public interface HeartbeatSupport {
    ByteBuf generateHeartBeatResponse(Frame reqFrame);
}
