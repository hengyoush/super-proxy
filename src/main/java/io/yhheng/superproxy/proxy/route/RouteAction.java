package io.yhheng.superproxy.proxy.route;

import io.yhheng.superproxy.protocol.Header;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.loadbalance.LoadBalance;

import java.util.List;

public interface RouteAction {
    /**
     * @return 上游集群使用的协议
     */
    Protocol upstreamProtocol();

    /**
     * @return 上游集群名称
     */
    List<String> upstreamClusterNames();

    /**
     * 对多个上游集群使用的负载均衡策略
     * @return 负载均衡策略
     */
    LoadBalance loadBalance();

    /**
     * 重写header
     * @param header 请求header
     */
    void rewrite(Header header);

    String getClusterName();
}
