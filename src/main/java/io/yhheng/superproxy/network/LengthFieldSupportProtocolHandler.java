package io.yhheng.superproxy.network;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.yhheng.superproxy.protocol.LengthFieldSupport;
import io.yhheng.superproxy.protocol.Protocol;

public class LengthFieldSupportProtocolHandler extends LengthFieldBasedFrameDecoder {
    public LengthFieldSupportProtocolHandler(Listener listener) {
        super(lengthFieldSupport(listener.downstreamProtocol()).maxFrameLength(),
                lengthFieldSupport(listener.downstreamProtocol()).frameLengthFieldOffset(),
                lengthFieldSupport(listener.downstreamProtocol()).frameLengthFieldLength());
    }

    private static LengthFieldSupport lengthFieldSupport(Protocol protocol) {
        return ((LengthFieldSupport) protocol);
    }
}
