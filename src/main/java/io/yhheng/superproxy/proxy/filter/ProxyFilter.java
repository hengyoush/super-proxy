package io.yhheng.superproxy.proxy.filter;

import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.proxy.ProxyPhase;

import java.util.Set;

public interface ProxyFilter {
    FilterStatus filter(Frame frame);
    Set<ProxyPhase> phases();

    enum FilterStatus {
        CONTINUE, STOP;
    }
}
