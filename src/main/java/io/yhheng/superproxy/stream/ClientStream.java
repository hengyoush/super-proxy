package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.network.ConnectionPool;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.retry.RetryState;

import java.util.concurrent.atomic.AtomicReference;

public class ClientStream extends Stream implements StreamReceiveListener {
    private UpstreamRequest upstreamRequest;
    private StreamSender streamSender;
    private ServerStream serverStream;
    private Protocol upstreamProtocol;
    private ConnectionPool connectionPool;
    private AtomicReference<StreamResetReason> updater = new AtomicReference<>(null);
    private RetryState retryState;

    public ClientStream() {
        // TODO upstreamRequest 需要在这里初始化
        super();
        setId(upstreamRequest.getFrame().getHeader().getRequestId());
        RetryState retryState = new RetryState();
        retryState.setCluster(upstreamRequest.getHost().cluster());
        retryState.setReqHeader(upstreamRequest.getFrame().getHeader());
        retryState.setRetryPolicy(null);
        retryState.setRetryOn(retryState.getRetryPolicy().retryOn());
        retryState.setRetryRemaining(retryState.getRetryPolicy().numRetries());
    }

    @Override
    public void onReceive(Frame frame) {
        serverStream.receiveResponse(frame);
    }

    @Override
    public void onDecodeError(Frame frame) {

    }


    public void appendRequest(boolean endStream) {
        streamSender.send(upstreamRequest);
    }

    public void setUpstreamRequest(UpstreamRequest upstreamRequest) {
        this.upstreamRequest = upstreamRequest;
    }

    public void setStreamSender(StreamSender streamSender) {
        this.streamSender = streamSender;
    }

    @Override
    public void reset(StreamResetReason resetReason) {
        if (resetReason.isDownstreamClose()) {
            serverStream.reset(resetReason);
        } else {
            if (updater.compareAndSet(null, resetReason)) {

            }
        }
    }

    public void setServerStream(ServerStream serverStream) {
        this.serverStream = serverStream;
    }

    public StreamResetReason getResetReason() {
        return updater.get();
    }

    public RetryState getRetryState() {
        return retryState;
    }

    public void setRetryState(RetryState retryState) {
        this.retryState = retryState;
    }
}
