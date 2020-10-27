package io.yhheng.superproxy.proxy.retry;

public interface RetryPolicy {
    boolean retryOn();
    Long retryTimeout();
    int numRetries();
    BackOff backoff();
}
