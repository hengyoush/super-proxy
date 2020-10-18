package io.yhheng.superproxy.proxy.filter;

import io.yhheng.superproxy.stream.StreamPhase;

import java.util.Collections;
import java.util.List;

public class StreamFilterManager {
    public List<ProxyFilter> getFilters(StreamPhase streamPhase) {
        return Collections.emptyList();
    }

    public void register(ProxyFilter proxyFilter) {

    }
}
