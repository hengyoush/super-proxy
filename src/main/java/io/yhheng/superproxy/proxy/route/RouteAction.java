package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Protocol;

public interface RouteAction {
    /**
     * @return 上游集群使用的协议
     */
    Protocol upstreamProtocol();

    String getClusterName();

    void processFrame(Frame frame);
}
