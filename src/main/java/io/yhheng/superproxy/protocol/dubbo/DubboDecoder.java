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

import static io.yhheng.superproxy.protocol.dubbo.DubboProtocolConstants.*;

public class DubboDecoder extends AbstractDecoder {

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
        DubboHeader header = new DubboHeader();
        header.setReq(isRequest);
        header.setTwoway(isTwoway);
        header.setEvent(isEvent);
        header.setRequestId(byteBuf.getLong(4));
        header.setStatus(byteBuf.getByte(3));
        header.setSerializerId(b & SERIALIZATION_MASK);
        if (isRequest) {
            return decodeRequest(byteBuf, header);
        } else {
            return decodeResponse(byteBuf, header);
        }
    }

    private DecodeResult decodeRequest(ByteBuf byteBuf, DubboHeader header) {
        try {
            Hessian2ObjectInput objectInput =
                    new Hessian2ObjectInput(new ByteBufInputStream(byteBuf, byteBuf.readableBytes()));
            int savedIndex = byteBuf.readerIndex(); // 在body的开始处
            header.setDubboVersion(objectInput.readUTF());
            header.setInterfaceName(objectInput.readUTF());
            header.setVersion(objectInput.readUTF());
            header.setGroup(""); // TODO
            Frame frame = new DubboFrame();
            byteBuf.readerIndex(savedIndex);
            frame.setData(byteBuf);
            frame.setHeader(header);
            DecodeResult decodeResult = new DecodeResult();
            decodeResult.setDecodeStatus(DecodeStatus.COMPLETE);
            decodeResult.setFrame(frame);
            decodeResult.setStreamType(header.isTwoway() ? StreamType.Request : StreamType.RequestOneWay);
            return decodeResult;
        } catch (Throwable e) {
            // TODO broken request
            return null;
        }
    }

    private DecodeResult decodeResponse(ByteBuf byteBuf, DubboHeader header) {
        try {
            Frame frame = new DubboFrame();
            frame.setHeader(header);
            frame.setData(byteBuf);
            frame.setRawBuf(byteBuf);
            DecodeResult decodeResult = new DecodeResult();
            decodeResult.setDecodeStatus(DecodeStatus.COMPLETE);
            decodeResult.setFrame(frame);
            decodeResult.setStreamType(StreamType.Response);
            return decodeResult;
        } catch (Throwable e) {
            // TODO broken response
            return null;
        }
    }

    @Override
    public Protocol protocol() {
        return Protocols.INSTANCE.get("dubbo");
    }
}
