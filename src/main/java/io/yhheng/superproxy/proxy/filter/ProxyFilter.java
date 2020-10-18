package io.yhheng.superproxy.proxy.filter;

import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.stream.StreamPhase;

import java.util.Set;

public interface ProxyFilter {
    FilterStatus filter(Frame frame);
    Set<StreamPhase> phases();

    enum FilterStatus {
        CONTINUE, STOP;
    }
}
