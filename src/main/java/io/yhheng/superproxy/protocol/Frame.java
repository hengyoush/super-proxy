package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;

public abstract class Frame {
    private Header header;
    private ByteBuf data;
    private ByteBuf rawBuf;
    private Protocol protocol;

    public ByteBuf getData() {
        return data;
    }

    public void setData(ByteBuf buf) {
        this.data = buf;
    }

    public ByteBuf getRawBuf() {
        return rawBuf;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setRawBuf(ByteBuf rawBuf) {
        this.rawBuf = rawBuf;
    }

    public abstract boolean isHeartBeat();

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
}
