package io.yhheng.superproxy.protocol.dubbo;

import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectInput;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.yhheng.superproxy.io.Bytes;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.stream.StreamType;

import java.io.IOException;

public class DubboDecoder implements Decoder {
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
    public DecodeResult decode(ByteBuf byteBuf) throws IOException {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes < HEADER_LENGTH) {
            return DecodeResult.NEED_MORE_INPUT;
        }
        byte[] header = new byte[HEADER_LENGTH];
        byteBuf.readBytes(header);

        boolean isRequest = (header[2] & FLAG_REQUEST) == FLAG_REQUEST;
        boolean isTwoway = (header[2] & FLAG_TWOWAY) == FLAG_TWOWAY;
        boolean isEvent = (header[2] & FLAG_EVENT) == FLAG_EVENT;
        int len = Bytes.bytes2int(header, 12);
        int totalBytes = len + HEADER_LENGTH;
        if (readableBytes < totalBytes) {
            return DecodeResult.NEED_MORE_INPUT;
        }

        if (isRequest) {
            // TODO close stream?
            Hessian2ObjectInput objectInput = new Hessian2ObjectInput(new ByteBufInputStream(byteBuf, len));
            int save = byteBuf.readerIndex();
            byte[] rawBody = new byte[len];
            byteBuf.readBytes(rawBody, 0, len);
            byteBuf.readerIndex(save);
            DubboHeader h = new DubboHeader();
            h.setFrameworkVersion(objectInput.readUTF());
//            h.setGroup(); TODO handle group
            h.setServiceName(objectInput.readUTF());
            h.setVersion(objectInput.readUTF());
            h.setEvent(isEvent);

            Frame frame = new Frame();
            frame.setData(byteBuf);
            frame.setHeader(h);
            DecodeResult decodeResult = new DecodeResult();
            decodeResult.setDecodeStatus(DecodeStatus.COMPLETE);
            decodeResult.setFrame(frame);
            decodeResult.setStreamType(StreamType.Request);
            return decodeResult;
        } else {
            // TODO decode response
        }

        return null;
    }
}
