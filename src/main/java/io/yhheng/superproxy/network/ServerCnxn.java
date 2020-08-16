package io.yhheng.superproxy.network;

import io.yhheng.superproxy.protocol.Protocol;

import java.net.InetAddress;

public abstract class ServerCnxn {
    private InetAddress inetAddress;
    private Protocol protocol;

    abstract void startup();

    abstract void shutdown();

}
