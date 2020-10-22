package io.yhheng.superproxy.protocol.dubbo;

import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Encoder;
import io.yhheng.superproxy.protocol.Protocol;

public class DubboProtocol implements Protocol {
    @Override
    public Decoder getDecoder() {
        return new DubboDecoder();
    }

    @Override
    public Encoder getEncoder() {
        return null;
    }
}
