package io.yhheng.superproxy.proxy;

import io.yhheng.superproxy.cluster.ClusterManager;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.proxy.filter.ProxyFilter;
import io.yhheng.superproxy.proxy.filter.ProxyFilters;
import io.yhheng.superproxy.proxy.retry.RetryState;
import io.yhheng.superproxy.proxy.route.Route;
import io.yhheng.superproxy.proxy.route.Routers;
import io.yhheng.superproxy.stream.ClientStream;
import io.yhheng.superproxy.stream.ServerStream;
import io.yhheng.superproxy.stream.StreamResetReason;
import io.yhheng.superproxy.stream.StreamSenderImpl;
import io.yhheng.superproxy.stream.UpstreamRequest;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static io.yhheng.superproxy.proxy.ProxyPhase.OneWay;
import static io.yhheng.superproxy.proxy.ProxyPhase.UpstreamResponse;
import static io.yhheng.superproxy.proxy.ProxyPhase.WaitResponse;

public class Proxy {
    private Routers routers;
    private ClusterManager clusterManager;
    private ProxyFilters proxyFilters;

    public Proxy(Routers routers,
                 ClusterManager clusterManager,
                 ProxyFilters proxyFilters) {
        this.routers = routers;
        this.clusterManager = clusterManager;
        this.proxyFilters = proxyFilters;
    }

    public void proxy(ServerStream serverStream) {
        proxy(serverStream, ProxyPhase.Init);
    }

    private ProxyPhase proxy(ServerStream ss, ProxyPhase proxyPhase) {
        for (int i = 0; i < ProxyPhase.End.ordinal() - ProxyPhase.Init.ordinal(); i++) {
            switch (Objects.requireNonNull(proxyPhase)) {
                case Init: proxyPhase = proxyPhase.next(); break;
                case PreRoute: {
                    runFilters(ss.getFrame(), proxyPhase);

                    ProxyPhase ph;
                    if ((ph = processError(ss)) != proxyPhase) {
                        return ph;
                    }
                    ss.setCurrentProxyPhase(proxyPhase = proxyPhase.next()); break;
                }
                case MatchRoute: {
                    matchRoute(ss);

                    ProxyPhase ph;
                    if ((ph = processError(ss)) != proxyPhase) {
                        return ph;
                    }
                    ss.setCurrentProxyPhase(proxyPhase = proxyPhase.next()); break;

                }
                case AfterRoute: {
                    runFilters(ss.getFrame(), proxyPhase);

                    ProxyPhase ph;
                    if ((ph = processError(ss)) != proxyPhase) {
                        return ph;
                    }
                    ss.setCurrentProxyPhase(proxyPhase = proxyPhase.next()); break;
                }
                case ChooseHost: {
                    chooseHost(ss);

                    ProxyPhase ph;
                    if ((ph = processError(ss)) != proxyPhase) {
                        return ph;
                    }
                    ss.setCurrentProxyPhase(proxyPhase = proxyPhase.next()); break;
                }
                case AfterChooseHost: {
                    runFilters(ss.getFrame(), proxyPhase);

                    ProxyPhase ph;
                    if ((ph = processError(ss)) != proxyPhase) {
                        return ph;
                    }
                    ss.setCurrentProxyPhase(proxyPhase = proxyPhase.next()); break;
                }
                case SendUpstreamRequest: {
                    createClientStream(ss);
                    sendRequest(ss);

                    ProxyPhase ph;
                    if ((ph = processError(ss)) != proxyPhase) {
                        return ph;
                    }
                    if (ss.isTwoway()) {
                        ss.setCurrentProxyPhase(WaitResponse);
                    } else {
                        ss.setCurrentProxyPhase(OneWay);
                    }
                    break;
                }
                case Retry: {
                    retry(ss);
                    ProxyPhase ph;
                    if ((ph = processError(ss)) != proxyPhase) {
                        return ph;
                    }
                    if (ss.isTwoway()) {
                        ss.setCurrentProxyPhase(WaitResponse);
                    } else {
                        ss.setCurrentProxyPhase(OneWay);
                    }
                    break;
                }
                case WaitResponse: {
                    ss.setCurrentProxyPhase(WaitResponse);
                    return WaitResponse;
                }
                case OneWay: {
                    ss.cleanStream();
                    ss.setCurrentProxyPhase(OneWay);
                    return OneWay;
                }
                case UpstreamResponse:{
                    ss.setCurrentProxyPhase(UpstreamResponse);
                    runFilters(ss.getUpstreamResponse(), proxyPhase);
                    ss.getServerConnection().write(ss.getUpstreamResponse().getRawBuf());

                    ProxyPhase ph;
                    if ((ph = processError(ss)) != proxyPhase) {
                        return ph;
                    }
                    ss.cleanStream();
                    ss.setCurrentProxyPhase(proxyPhase = proxyPhase.next()); break;
                }
                case End:{
                    return ProxyPhase.End;
                }
                default: {
                    throw new IllegalStateException("can't reach here");
                }
            }
        }
        return ProxyPhase.End;
    }

    public void proxyUpResponse(ServerStream serverStream) {
        proxy(serverStream, ProxyPhase.UpstreamResponse);
    }

    private void runFilters(Frame frame, ProxyPhase phase) {
        List<ProxyFilter> filters = proxyFilters.getFilters(phase);
        for (int i = 0; i < filters.size(); i++) {
            ProxyFilter proxyFilter = filters.get(i);
            ProxyFilter.FilterStatus filterStatus = proxyFilter.filter(frame);
            if (filterStatus == ProxyFilter.FilterStatus.STOP) {
                break;
            }
        }
    }

    private void matchRoute(ServerStream serverStream) {
        Route route = routers.match(serverStream.getFrame().getHeader());
        if (route == null) {
            // TODO no upstream cluster found, end the stream
        }

        String clusterName = route.routerAction().getClusterName();
        serverStream.setUpstreamCluster(clusterManager.getCluster(clusterName));
        serverStream.setRoute(route);
    }

    private void chooseHost(ServerStream serverStream) {
        if (serverStream.getUpstreamCluster() == null) {
            serverStream.sendHijackReply("no route find");
            return;
        }
        var upstreamHost = serverStream.getUpstreamCluster().selectHost();
        serverStream.setUpstreamHost(upstreamHost);
    }

    private void retry(ServerStream serverStream) {
        // TODO sleep?
        chooseHost(serverStream);
        createClientStream(serverStream);
        sendRequest(serverStream);
    }

    private void createClientStream(ServerStream serverStream) {
        var upstreamRequest = new UpstreamRequest();
        upstreamRequest.setFrame(serverStream.getFrame());
        upstreamRequest.setHost(serverStream.getUpstreamHost());
        var clientStream = new ClientStream();
        clientStream.setStreamSender(new StreamSenderImpl());
        clientStream.setUpstreamRequest(upstreamRequest);
        clientStream.setServerStream(serverStream);
        serverStream.setClientStream(clientStream);
    }

    private void sendRequest(ServerStream serverStream) {
        serverStream.getClientStream().appendRequest(true);
    }

    private ProxyPhase processError(ServerStream serverStream) {
        AtomicReference<StreamResetReason> downstreamResetReason = serverStream.getResetReason();
        if (downstreamResetReason.get() != null) {
            StreamResetReason reason = downstreamResetReason.get();
            if (reason.isDownstreamClose()) {
                // stream已经关闭的情况下， 无需重试, 如果是自己关闭的话，那么需要发送响应
                if (reason == StreamResetReason.DownstreamConnectionCloseLocally) {
                    serverStream.sendHijackReply("infinity proxy already closed");
                }
                return ProxyPhase.End;
            }
        }

        ClientStream clientStream = serverStream.getClientStream();
        if (clientStream != null) {
            StreamResetReason upstreamResetReason = clientStream.getResetReason();
            if (upstreamResetReason != null) {
                RetryState retryState = clientStream.getRetryState().retry(upstreamResetReason);
                if (retryState.isRetryOn()) {
                    // retry
                    return ProxyPhase.Retry;
                } else {
                    return ProxyPhase.End;
                }
            }
        }

        return null;
    }
}
