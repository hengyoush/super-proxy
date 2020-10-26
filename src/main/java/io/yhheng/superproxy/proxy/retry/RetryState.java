package io.yhheng.superproxy.proxy.retry;

import io.yhheng.superproxy.cluster.Cluster;
import io.yhheng.superproxy.protocol.Header;
import io.yhheng.superproxy.stream.StreamResetReason;

public class RetryState {
    private RetryPolicy retryPolicy;
    private boolean retryOn;
    private Header reqHeader;
    private int retryRemaining;
    private Cluster cluster;

    public RetryState retry(StreamResetReason reason) {
        if (shouldRetry(reason)) {
            this.retryRemaining--;
        }
        return this;
    }

    private boolean shouldRetry(StreamResetReason reason) {
        return false;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public boolean isRetryOn() {
        return retryOn;
    }

    public void setRetryOn(boolean retryOn) {
        this.retryOn = retryOn;
    }

    public Header getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(Header reqHeader) {
        this.reqHeader = reqHeader;
    }

    public int getRetryRemaining() {
        return retryRemaining;
    }

    public void setRetryRemaining(int retryRemaining) {
        this.retryRemaining = retryRemaining;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
