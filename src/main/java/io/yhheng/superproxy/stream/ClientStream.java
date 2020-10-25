package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.network.ConnectionPool;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Protocol;

public class ClientStream extends Stream implements StreamReceiveListener {
    private UpstreamRequest upstreamRequest;
    private StreamSender streamSender;
    private ServerStream serverStream;
    private Protocol upstreamProtocol;
    private ConnectionPool connectionPool;

    public ClientStream() {
        // TODO upstreamRequest 需要在这里初始化
        super();
        setId(upstreamRequest.getFrame().getHeader().getRequestId());
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
        serverStream.reset(resetReason);
    }

    public void setServerStream(ServerStream serverStream) {
        this.serverStream = serverStream;
    }
}
