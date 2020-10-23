package io.yhheng.superproxy.protocol.dubbo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.yhheng.superproxy.protocol.Encoder;
import io.yhheng.superproxy.protocol.Header;

import static io.yhheng.superproxy.protocol.dubbo.DubboProtocolConstants.HEADER_LENGTH;

public class DubboEncoder implements Encoder {
    @Override
    public ByteBuf encode(Header header, ByteBuf body) {
        assert header instanceof DubboHeader;
        DubboHeader dubboHeader = (DubboHeader) header;
        if (dubboHeader.isEvent() && body == null) {
            // heartbeat
            return generateHeartBeat(dubboHeader);
        } else {
            return generateNormal(dubboHeader, body);
        }
    }

    private ByteBuf generateHeartBeat(DubboHeader header) {
        ByteBuf headerBuffer = Unpooled.buffer(HEADER_LENGTH, HEADER_LENGTH);
        headerBuffer.writeShort(DubboProtocolConstants.MAGIC);
        byte b3 = 0;
        if (header.isReq()) {
            b3 |= DubboProtocolConstants.FLAG_REQUEST;
            b3 |= DubboProtocolConstants.FLAG_TWOWAY;
        }
        b3 |= DubboProtocolConstants.FLAG_EVENT;
        headerBuffer.writeByte(b3);
        headerBuffer.writeByte(0);
        headerBuffer.writeLong(header.getRequestId());
        ByteBuf body = HessianUtils.writeNull();
        headerBuffer.writeInt(body.readableBytes());
        return Unpooled.compositeBuffer().addComponent(headerBuffer).addComponent(body);
    }

    private ByteBuf generateNormal(DubboHeader header, ByteBuf body) {
        ByteBuf headerBuffer = Unpooled.buffer(HEADER_LENGTH, HEADER_LENGTH);
        headerBuffer.writeShort(DubboProtocolConstants.MAGIC);
        byte b3 = 0;
        if (header.isReq()) {
            b3 |= DubboProtocolConstants.FLAG_REQUEST;
        }
        if (header.isTwoway()) {
            b3 |= DubboProtocolConstants.FLAG_TWOWAY;
        }
        if (header.isEvent()) {
            b3 |= DubboProtocolConstants.FLAG_EVENT;
        }
        b3 |= header.getSerializerId();
        headerBuffer.writeByte(b3);
        headerBuffer.writeByte(header.getStatus());
        headerBuffer.writeLong(header.getRequestId());
        headerBuffer.writeInt(body.readableBytes());
        return Unpooled.compositeBuffer().addComponent(headerBuffer).addComponent(body);
    }
}
