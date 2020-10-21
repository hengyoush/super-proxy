package io.yhheng.superproxy.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.yhheng.superproxy.Server;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.List;

public class NettyListenerImpl implements Listener {
    private static final Logger log = LoggerFactory.getLogger(NettyListenerImpl.class);
    private String name;
    private SocketAddress bindAddr;
    private List<NetworkFilter> networkFilters;
    private List<ListenerEventListener> listenerEventListeners;
    private Protocol protocol;
    private Proxy proxy;

    // netty
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyListenerImpl(String name,
                             SocketAddress bindAddr,
                             List<NetworkFilter> networkFilters,
                             List<ListenerEventListener> listenerEventListeners,
                             Protocol protocol,
                             Proxy proxy) {
        this.name = name;
        this.bindAddr = bindAddr;
        this.networkFilters = networkFilters;
        this.listenerEventListeners = listenerEventListeners;
        this.protocol = protocol;
        this.proxy = proxy;
    }

    public void listen() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
        serverBootstrap.group(bossGroup, workerGroup)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NetworkHandler(NettyListenerImpl.this));
                    }
                });
        serverBootstrap.bind(bindAddr).addListener(future -> {
            if (future.isSuccess()) {
                listenerEventListeners.forEach(i -> i.onListenerStarted(NettyListenerImpl.this));
            } else {
                log.error("启动listener:{}失败,绑定地址:{}", name, bindAddr, future.cause());
                shutdown();
            }
        });

    }

    public void shutdown() {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully().syncUninterruptibly();
        }
    }

    @Override
    public List<NetworkFilter> networkFilters() {
        return null;
    }

    @Override
    public List<ListenerEventListener> listenerEventListeners() {
        return listenerEventListeners;
    }

    @Override
    public Protocol downstreamProtocol() {
        return protocol;
    }

    @Override
    public Server server() {
        return null;
    }

    public List<NetworkFilter> getNetworkFilters() {
        return networkFilters;
    }
}
