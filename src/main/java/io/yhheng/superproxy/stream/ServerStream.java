package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.cluster.Cluster;
import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.cluster.Host;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.proxy.route.Route;
import io.yhheng.superproxy.proxy.route.Routers;

public class ServerStream implements StreamReceiveListener {
    private Long id;
    private Connection serverConnection;
    private Frame frame;
    private Routers routers;
    private ClusterManager clusterManager;
    private ActiveStreamManager activeStreamManager;

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

    @Override
    public void onReceive(Frame frame) {
        // TODO 可以用一个线程池

        // match route(select cluster)
        matchRoute();

        // choose host(in-cluster load balance
        chooseHost();

        // create new client stream
        createClientStream();

    }

    @Override
    public void onDecodeError(Frame frame) {

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
        Connection connection = clusterManager.initialzeConnectionForHost(upstreamHost);
    }

    private void createClientStream() {
        this.upstreamRequest = new UpstreamRequest();
        upstreamRequest.setFrame(frame);
        upstreamRequest.setHost(upstreamHost);
        ClientStream clientStream = new ClientStream();
        clientStream.setStreamSender(null);
        clientStream.setUpstreamRequest(upstreamRequest);
        clientStream.appendHeaders(false);
        clientStream.appendData(true);
    }
}
