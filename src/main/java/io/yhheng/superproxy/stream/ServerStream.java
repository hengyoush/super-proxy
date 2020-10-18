package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.cluster.Cluster;
import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.cluster.Host;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.proxy.route.Route;
import io.yhheng.superproxy.proxy.route.Routers;
import io.yhheng.superproxy.proxy.filter.ProxyFilter;
import io.yhheng.superproxy.proxy.filter.StreamFilterManager;

import java.util.List;

public class ServerStream implements StreamReceiveListener {
    private Long id;
    private Connection serverConnection;
    private Frame frame;
    private Routers routers;
    private ClusterManager clusterManager;
    private ActiveStreamManager activeStreamManager;
    private StreamFilterManager streamFilterManager;

    // set in processing
    private Cluster upstreamCluster;
    private Host upstreamHost;
    private UpstreamRequest upstreamRequest;
    private ClientStream clientStream;

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

        runFilters(StreamPhase.PreRoute);

        // match route(select cluster)
        matchRoute();

        runFilters(StreamPhase.AfterRoute);

        // choose host(in-cluster load balance
        chooseHost();

        runFilters(StreamPhase.AfterChooseHost);

        // create new client stream
        createClientStream();

        // send request
        sendRequest();
    }

    @Override
    public void onDecodeError(Frame frame) {

    }

    public void receiveResponse(Frame frame) {
        // handle Frame
        runFilters(frame, StreamPhase.UpstreamResponse);
        serverConnection.write(frame.getRawBuf());
    }

    private void runFilters(StreamPhase phase) {
        runFilters(frame, phase);
    }

    private void runFilters(Frame frame, StreamPhase phase) {
        List<ProxyFilter> filters = streamFilterManager.getFilters(phase);
        for (int i = 0; i < filters.size(); i++) {
            ProxyFilter proxyFilter = filters.get(i);
            ProxyFilter.FilterStatus filterStatus = proxyFilter.filter(frame);
            if (filterStatus == ProxyFilter.FilterStatus.STOP) {
                break;
            }
        }
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
        this.clientStream = new ClientStream();
        clientStream.setStreamSender(null);
        clientStream.setUpstreamRequest(upstreamRequest);
        activeStreamManager.addClientStream(clientStream);

    }

    private void sendRequest() {
        clientStream.appendRequest(true);
    }
}
