package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.protocol.Frame;

public interface StreamReceiveListener {
    void onReceive(Frame frame);

    void onDecodeError(Frame frame);
}
