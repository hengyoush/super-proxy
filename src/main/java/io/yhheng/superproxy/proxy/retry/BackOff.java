package io.yhheng.superproxy.proxy.retry;

public interface BackOff {
    Long minMs();
    Long maxMs();
}
