package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.protocol.Frame;

public interface ClientStreamConnection extends ServerStreamConnection {
    ClientStream newStream(Frame frame);
}
