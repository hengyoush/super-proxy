package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Protocol;

public abstract class RouteActionImpl implements RouteAction {
    private Protocol upstreamProtocol;
    private String upstreamClusterName;

    public RouteActionImpl(Protocol upstreamProtocol, String upstreamClusterName) {
        this.upstreamProtocol = upstreamProtocol;
        this.upstreamClusterName = upstreamClusterName;
    }

    @Override
    public Protocol upstreamProtocol() {
        return upstreamProtocol;
    }

    @Override
    public String getClusterName() {
        return upstreamClusterName;
    }

    @Override
    public abstract void processFrame(Frame frame);
}
