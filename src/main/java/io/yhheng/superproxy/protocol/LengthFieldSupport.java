package io.yhheng.superproxy.protocol;

public interface LengthFieldSupport {
    int maxFrameLength();

    int frameLengthFieldOffset();

    int frameLengthFieldLength();
}
