package io.yhheng.superproxy.stream;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.cluster.Cluster;
import io.yhheng.superproxy.cluster.Host;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.proxy.Proxy;
import io.yhheng.superproxy.proxy.ProxyPhase;
import io.yhheng.superproxy.proxy.route.Route;

import java.util.concurrent.atomic.AtomicReference;

public class ServerStream extends Stream implements StreamReceiveListener {
    private Long id;
    private final Connection serverConnection;
    private final Proxy proxy;
    private final StreamConnection streamConnection;

    private boolean twoway;

    private Frame frame;
    private Frame upstreamResponse;

    // set in processing
    private Route route;
    private Cluster upstreamCluster;
    private Host upstreamHost;
    private UpstreamRequest upstreamRequest;
    private ClientStream clientStream;
    private ProxyPhase currentProxyPhase;

    private AtomicReference<StreamResetReason> resetReason = new AtomicReference<>(null);

    public ServerStream(Connection serverConnection, Proxy proxy, StreamConnection streamConnection) {
        this(serverConnection, proxy, streamConnection, true);
    }

    public ServerStream(Connection serverConnection, Proxy proxy, StreamConnection streamConnection, boolean twoway) {
        this.serverConnection = serverConnection;
        this.proxy = proxy;
        this.streamConnection = streamConnection;
        this.twoway = twoway;
    }

    @Override
    public void onReceive(Frame frame) {
        this.id = frame.getHeader().getRequestId();
        setFrame(frame);
        proxy.proxy(this);
    }

    @Override
    public void onDecodeError(Frame frame) {

    }

    public void receiveResponse(Frame frame) {
        if (!isReset()) {
            // handle Frame
            setUpstreamResponse(frame);
            proxy.proxyUpResponse(this);
        }
    }

    public void sendHijackReply(String msg) {
        ByteBuf byteBuf = frame.getProtocol().generateFailedResponse(frame.getHeader(), msg);
        serverConnection.write(byteBuf);
    }

    public void cleanStream() {
        if (isTwoway()) {
            streamConnection.activeStreamManager().removeStream(getId());
        }
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public void setUpstreamCluster(Cluster upstreamCluster) {
        this.upstreamCluster = upstreamCluster;
    }

    public void setUpstreamHost(Host upstreamHost) {
        this.upstreamHost = upstreamHost;
    }

    public void setUpstreamRequest(UpstreamRequest upstreamRequest) {
        this.upstreamRequest = upstreamRequest;
    }

    public void setClientStream(ClientStream clientStream) {
        if (twoway) {
            streamConnection.activeStreamManager().addClientStream(clientStream);
        }
        this.clientStream = clientStream;
    }

    public void setUpstreamResponse(Frame upstreamResponse) {
        this.upstreamResponse = upstreamResponse;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void reset(StreamResetReason resetReason) {
        if (!this.resetReason.compareAndSet(null, resetReason)) {
            // TODO log here already reset [info]
        }

         cleanStream();
    }

    public Connection getServerConnection() {
        return serverConnection;
    }

    public Frame getFrame() {
        return frame;
    }

    public Frame getUpstreamResponse() {
        return upstreamResponse;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public Cluster getUpstreamCluster() {
        return upstreamCluster;
    }

    public Host getUpstreamHost() {
        return upstreamHost;
    }

    public UpstreamRequest getUpstreamRequest() {
        return upstreamRequest;
    }

    public ClientStream getClientStream() {
        return clientStream;
    }


    public boolean isTwoway() {
        return twoway;
    }

    public void setTwoway(boolean twoway) {
        this.twoway = twoway;
    }

    public AtomicReference<StreamResetReason> getResetReason() {
        return resetReason;
    }

    public boolean isReset() {
        return resetReason.get() != null;
    }

    public void setCurrentProxyPhase(ProxyPhase currentProxyPhase) {
        this.currentProxyPhase = currentProxyPhase;
    }

    public ProxyPhase getCurrentProxyPhase() {
        return currentProxyPhase;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
