package io.yhheng.superproxy.protocol.dubbo;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.model.Request;
import io.yhheng.superproxy.protocol.Protocol;

public class DubboProtocol implements Protocol {
    @Override
    public Request decode(ByteBuf buf) {
        return null;
    }
}
