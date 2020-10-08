package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.cluster.Cluster;
import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.cluster.Host;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.proxy.UpstreamRequest;
import io.yhheng.superproxy.proxy.route.Route;
import io.yhheng.superproxy.proxy.route.Routers;

public class Downstream {
    private Long id;
    private Connection serverConnection;
    private Frame frame;
    private Routers routers;
    private ClusterManager clusterManager;

    // set in processing
    private Cluster upstreamCluster;
    private Host upstreamHost;
    private UpstreamRequest upstreamRequest;

    public Connection getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(Connection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public UpstreamRequest getUpstreamRequest() {
        return upstreamRequest;
    }

    public Cluster getUpstreamCluster() {
        return upstreamCluster;
    }

    public Host getUpstreamHost() {
        return upstreamHost;
    }

    public void receiveRequest() {
        // TODO 可以用一个线程池

        // match route(select cluster)
        matchRoute();

        // choose host(in-cluster load balance
        chooseHost();

        // create upstream request
        createUpstreamRequest();
    }

    public void receiveResponse(Frame frame) {

    }

    private void matchRoute() {
        Route match = routers.match(frame.getHeader());
        if (match == null) {
            // TODO no upstream cluster found, end the stream
        }

        String clusterName = match.routerAction().getClusterName();
        this.upstreamCluster = clusterManager.getCluster(clusterName);
    }

    private void chooseHost() {
        this.upstreamHost = upstreamCluster.selectHost();
    }

    private void createUpstreamRequest() {
        this.upstreamRequest = new UpstreamRequest();
        upstreamRequest.setFrame(frame);
        upstreamRequest.setHost(upstreamHost);
    }

}
