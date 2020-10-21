package io.yhheng.superproxy.proxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.filter.ProxyFilter;
import io.yhheng.superproxy.proxy.filter.ProxyFilters;
import io.yhheng.superproxy.proxy.route.Route;
import io.yhheng.superproxy.proxy.route.Routers;
import io.yhheng.superproxy.stream.ClientStream;
import io.yhheng.superproxy.stream.ServerStream;
import io.yhheng.superproxy.stream.StreamPhase;
import io.yhheng.superproxy.stream.StreamSenderImpl;
import io.yhheng.superproxy.stream.UpstreamRequest;

import java.util.List;

public class Proxy {
    private Routers routers;
    private ClusterManager clusterManager;
    private ProxyFilters proxyFilters;

    public Proxy(Routers routers,
                 ClusterManager clusterManager,
                 ProxyFilters proxyFilters) {
        this.routers = routers;
        this.clusterManager = clusterManager;
        this.proxyFilters = proxyFilters;
    }

    public void proxy(ServerStream serverStream) {
        runFilters(serverStream.getFrame(), StreamPhase.PreRoute);
        matchRoute(serverStream);
        runFilters(serverStream.getFrame(), StreamPhase.AfterRoute);
        chooseHost(serverStream);
        runFilters(serverStream.getFrame(), StreamPhase.AfterChooseHost);
        createClientStream(serverStream);
        sendRequest(serverStream);
    }

    public void proxyUpResponse(ServerStream serverStream) {
        runFilters(serverStream.getUpstreamResponse(), StreamPhase.UpstreamResponse);
        serverStream.getServerConnection().write(serverStream.getUpstreamResponse().getRawBuf());
    }

    private void runFilters(Frame frame, StreamPhase phase) {
        List<ProxyFilter> filters = proxyFilters.getFilters(phase);
        for (int i = 0; i < filters.size(); i++) {
            ProxyFilter proxyFilter = filters.get(i);
            ProxyFilter.FilterStatus filterStatus = proxyFilter.filter(frame);
            if (filterStatus == ProxyFilter.FilterStatus.STOP) {
                break;
            }
        }
    }

    private void matchRoute(ServerStream serverStream) {
        Route match = routers.match(serverStream.getFrame().getHeader());
        if (match == null) {
            // TODO no upstream cluster found, end the stream
        }

        String clusterName = match.routerAction().getClusterName();
        serverStream.setUpstreamCluster(clusterManager.getCluster(clusterName));
    }

    private void chooseHost(ServerStream serverStream) {
        var upstreamHost = serverStream.getUpstreamCluster().selectHost();
        serverStream.setUpstreamHost(upstreamHost);
    }

    private void createClientStream(ServerStream serverStream) {
        var upstreamRequest = new UpstreamRequest();
        upstreamRequest.setFrame(serverStream.getFrame());
        upstreamRequest.setHost(serverStream.getUpstreamHost());
        var clientStream = new ClientStream();
        clientStream.setStreamSender(new StreamSenderImpl());
        clientStream.setUpstreamRequest(upstreamRequest);
        serverStream.setClientStream(clientStream);
    }

    private void sendRequest(ServerStream serverStream) {
        serverStream.getClientStream().appendRequest(true);
    }
}
