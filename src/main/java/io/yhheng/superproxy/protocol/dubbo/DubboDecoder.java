package io.yhheng.superproxy.protocol.dubbo;

import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectInput;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.yhheng.superproxy.protocol.AbstractDecoder;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.protocol.Protocols;
import io.yhheng.superproxy.stream.StreamType;

public class DubboDecoder extends AbstractDecoder {
    // header length.
    protected static final int HEADER_LENGTH = 16;
    // magic header.
    protected static final short MAGIC = (short) 0xdabb;
    // message flag.
    protected static final byte FLAG_REQUEST = (byte) 0x80;
    protected static final byte FLAG_TWOWAY = (byte) 0x40;
    protected static final byte FLAG_EVENT = (byte) 0x20;
    protected static final int SERIALIZATION_MASK = 0x1f;

    @Override
    public DecodeResult tryDecode(ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes < HEADER_LENGTH) {
            return DecodeResult.NEED_MORE_INPUT;
        }
        int len = byteBuf.getInt(12);
        int totalBytes = len + HEADER_LENGTH;
        if (readableBytes < totalBytes) {
            return DecodeResult.NEED_MORE_INPUT;
        }
        ByteBuf copied = byteBuf.copy(byteBuf.readerIndex(), totalBytes);
        byteBuf.readerIndex(byteBuf.readerIndex() + totalBytes);
        return doDecode(copied);
    }

    @Override
    public DecodeResult doDecode(ByteBuf byteBuf) {
        byte b = byteBuf.getByte(2);
        boolean isRequest = (b & FLAG_REQUEST) == FLAG_REQUEST;
        boolean isTwoway = (b & FLAG_TWOWAY) == FLAG_TWOWAY;
        boolean isEvent = (b & FLAG_EVENT) == FLAG_EVENT;
        byteBuf.readerIndex(HEADER_LENGTH);
        if (isRequest) {
           return decodeRequest(byteBuf, isTwoway, isEvent);
        } else {
            // TODO response handle
            return null;
        }
    }

    private DecodeResult decodeRequest(ByteBuf byteBuf, boolean isTwoway, boolean isEvent) {
        try {
            Hessian2ObjectInput objectInput =
                    new Hessian2ObjectInput(new ByteBufInputStream(byteBuf, byteBuf.readableBytes() - HEADER_LENGTH));
            int savedIndex = byteBuf.readerIndex();
            DubboHeader h = new DubboHeader();
            h.setFrameworkVersion(objectInput.readUTF());
            h.setServiceName(objectInput.readUTF());
            h.setVersion(objectInput.readUTF());
            h.setEvent(isEvent);
            h.setGroup(""); // TODO
            Frame frame = new Frame();
            byteBuf.readerIndex(savedIndex);
            frame.setData(byteBuf);
            frame.setHeader(h);
            DecodeResult decodeResult = new DecodeResult();
            decodeResult.setDecodeStatus(DecodeStatus.COMPLETE);
            decodeResult.setFrame(frame);
            decodeResult.setStreamType(StreamType.Request);
            return decodeResult;
        } catch (Throwable e) {
            // TODO broken request
            return null;
        }
    }

    @Override
    public Protocol protocol() {
        return Protocols.INSTANCE.get("dubbo");
    }
}
