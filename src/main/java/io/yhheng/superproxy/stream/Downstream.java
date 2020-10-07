package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.Frame;

public class Downstream {
    private Connection serverConnection;
    private Frame frame;

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

    public void receiveRequest() {
        // TODO 可以用一个线程池

        // match route(select cluster)

        // choose host(in-cluster load balance

        // send upstream request
    }

    public void receiveResponse(Frame frame) {

    }
}
