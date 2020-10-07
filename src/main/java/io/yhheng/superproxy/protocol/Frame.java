package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;

public class Frame {
    private Header header;
    private ByteBuf data;
    private ByteBuf rawBuf;

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

    boolean isHeartBeat() {
        return header.isHeartbeat();
    }
}
