package io.yhheng.superproxy.proxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.stream.ActiveStreamManager;
import io.yhheng.superproxy.stream.Downstream;
import io.yhheng.superproxy.stream.ServerStreamConnection;

import java.util.List;

public class Proxy {
    private Connection serverConnection;
    private Protocol downstreamProtocol;
    private Protocol upstreamProtocol;
    private ClusterManager clusterManager;
    // serverStream
    private ServerStreamConnection serverStreamConnection;
    private ActiveStreamManager activeStreamManager;

    public void newServerStreamConnection(Connection serverConnection) {

    }

    public ServerStreamConnection getServerStreamConnection() {
        return serverStreamConnection;
    }

    public void setServerConnection(Connection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void setDownstreamProtocol(Protocol downstreamProtocol) {
        this.downstreamProtocol = downstreamProtocol;
    }

    public void setUpstreamProtocol(Protocol upstreamProtocol) {
        this.upstreamProtocol = upstreamProtocol;
    }

    public void setClusterManager(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    public void setServerStreamConnection(ServerStreamConnection serverStreamConnection) {
        this.serverStreamConnection = serverStreamConnection;
    }


    public Connection getServerConnection() {
        return serverConnection;
    }

    public Protocol getDownstreamProtocol() {
        return downstreamProtocol;
    }

    public Protocol getUpstreamProtocol() {
        return upstreamProtocol;
    }

    public ClusterManager getClusterManager() {
        return clusterManager;
    }

    public ActiveStreamManager getActiveStreamManager() {
        return activeStreamManager;
    }
}
