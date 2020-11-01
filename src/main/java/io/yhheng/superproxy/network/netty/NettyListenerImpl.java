package io.yhheng.superproxy.network.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.yhheng.superproxy.Server;
import io.yhheng.superproxy.network.ConnectionEventListener;
import io.yhheng.superproxy.network.Listener;
import io.yhheng.superproxy.network.ListenerEventListener;
import io.yhheng.superproxy.network.NetworkReadFilter;
import io.yhheng.superproxy.protocol.LengthFieldSupport;
import io.yhheng.superproxy.protocol.LengthFieldSupportProtocol;
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
    private List<NetworkReadFilter> networkReadFilters;
    private List<ListenerEventListener> listenerEventListeners;
    private List<ConnectionEventListener> connectionEventListeners;
    private Protocol protocol;
    private Proxy proxy;

    // netty
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyListenerImpl(String name,
                             SocketAddress bindAddr,
                             List<NetworkReadFilter> networkReadFilters,
                             List<ListenerEventListener> listenerEventListeners,
                             List<ConnectionEventListener> connectionEventListeners,
                             Protocol protocol,
                             Proxy proxy) {
        this.name = name;
        this.bindAddr = bindAddr;
        this.networkReadFilters = networkReadFilters;
        this.listenerEventListeners = listenerEventListeners;
        this.connectionEventListeners = connectionEventListeners;
        this.protocol = protocol;
        this.proxy = proxy;
    }

    public void listen() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
        serverBootstrap.group(bossGroup, workerGroup)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelInitializer<SocketChannel> channelInitializer;
        if (protocol instanceof LengthFieldSupport) {
            channelInitializer = new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new LengthFieldSupportProtocolHandler((LengthFieldSupportProtocol) protocol));
                    ch.pipeline().addLast(new NetworkHandler(NettyListenerImpl.this));
                }
            };
        } else {
            channelInitializer = new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new NormalDecodeHandler(protocol));
                    ch.pipeline().addLast(new NetworkHandler(NettyListenerImpl.this));
                }
            };
        }
        serverBootstrap.childHandler(channelInitializer);
        serverBootstrap.bind(bindAddr).addListener(future -> {
            if (future.isSuccess()) {
                log.info("listener {} 启动成功", name);
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
    public List<NetworkReadFilter> networkReadFilters() {
        return null;
    }

    @Override
    public List<ListenerEventListener> listenerEventListeners() {
        return listenerEventListeners;
    }

    @Override
    public List<ConnectionEventListener> connectionEventListeners() {
        return connectionEventListeners;
    }

    @Override
    public Protocol downstreamProtocol() {
        return protocol;
    }

    @Override
    public Server server() {
        return null;
    }

    @Override
    public Proxy proxy() {
        return proxy;
    }

    public List<NetworkReadFilter> getNetworkReadFilters() {
        return networkReadFilters;
    }
}
