package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.network.ConnectionPool;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Protocol;

public class ClientStream implements StreamReceiveListener {
    private UpstreamRequest upstreamRequest;
    private StreamSender streamSender;
    private ServerStream serverStream;
    private Protocol upstreamProtocol;
    private ConnectionPool connectionPool;

    @Override
    public void onReceive(Frame frame) {

    }

    @Override
    public void onDecodeError(Frame frame) {

    }


    public void appendHeaders(boolean endStream) {

    }

    public void appendData(boolean endStream) {

    }

    public void setUpstreamRequest(UpstreamRequest upstreamRequest) {
        this.upstreamRequest = upstreamRequest;
    }

    public void setStreamSender(StreamSender streamSender) {
        this.streamSender = streamSender;
    }
}
