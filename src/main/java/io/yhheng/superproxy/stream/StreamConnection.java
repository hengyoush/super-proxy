package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Protocol;

public interface StreamConnection {
    void dispatch(DecodeResult decodeResult);
    Protocol protocol();
    ActiveStreamManager activeStreamManager();
    void close(boolean closeRemotely);
}
