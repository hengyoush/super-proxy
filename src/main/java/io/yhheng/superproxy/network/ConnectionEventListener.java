package io.yhheng.superproxy.network;

public interface ConnectionEventListener {

    void onEvent(ConnectionEvent event, Connection connection);

    enum ConnectionEvent {
        RemoteClose, LocalClose, OnConnect, Connected, ConnectionTimeout, ReadTimeout, WriteTimeout
    }
}
