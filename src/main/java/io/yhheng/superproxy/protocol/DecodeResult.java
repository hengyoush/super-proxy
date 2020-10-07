package io.yhheng.superproxy.protocol;

import io.yhheng.superproxy.stream.StreamType;

public class DecodeResult {
    public static final DecodeResult NEED_MORE_INPUT = new DecodeResult();
    private Frame frame;
    private Decoder.DecodeStatus decodeStatus;
    private StreamType streamType;
    private Throwable e;

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Decoder.DecodeStatus getDecodeStatus() {
        return decodeStatus;
    }

    public Throwable getException() {
        return e;
    }

    public void setDecodeStatus(Decoder.DecodeStatus decodeStatus) {
        this.decodeStatus = decodeStatus;
    }

    public void setE(Throwable e) {
        this.e = e;
    }

    public StreamType getStreamType() {
        return streamType;
    }

    public void setStreamType(StreamType streamType) {
        this.streamType = streamType;
    }
}

