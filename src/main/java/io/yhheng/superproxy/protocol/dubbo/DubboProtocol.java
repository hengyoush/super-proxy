package io.yhheng.superproxy.protocol.dubbo;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.config.ProtocolConfig;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Encoder;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Header;
import io.yhheng.superproxy.protocol.HeartbeatSupport;
import io.yhheng.superproxy.protocol.LengthFieldSupportProtocol;
import io.yhheng.superproxy.protocol.Protocol;

import java.util.function.Function;

public class DubboProtocol implements LengthFieldSupportProtocol, HeartbeatSupport {
    public DubboProtocol(ProtocolConfig protocolConfig) {
    }

    @Override
    public Decoder getDecoder() {
        return new DubboDecoder();
    }

    @Override
    public Encoder getEncoder() {
        return new DubboEncoder();
    }

    @Override
    public ByteBuf generateFailedResponse(Header header, String msg) {
        if (header instanceof DubboHeader) {
            return getEncoder().encode(header, HessianUtils.writeString(msg));
        } else {
            // TODO log warn here
            return null;
        }
    }

    @Override
    public int maxFrameLength() {
        return 100 * 1024 * 1024;
    }

    @Override
    public int frameLengthFieldOffset() {
        return 12;
    }

    @Override
    public int frameLengthFieldLength() {
        return 4;
    }

    @Override
    public ByteBuf generateHeartBeatResponse(Frame reqFrame) {
        Header header = reqFrame.getHeader();
        if (header instanceof DubboHeader) {
            return getEncoder().encode(header, null);
        } else {
            // TODO log warn here
            return null;
        }
    }

    public static Function<ProtocolConfig, Protocol> factory() {
        return DubboProtocol::new;
    }
}
