package io.yhheng.superproxy.proxy;

import io.yhheng.superproxy.cluster.Host;
import io.yhheng.superproxy.protocol.Frame;

public class UpstreamRequest {
    private Host host;
    private Frame frame;

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}
