package io.yhheng.superproxy.stream;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Header;
import io.yhheng.superproxy.protocol.HeartbeatSupport;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplexStreamConnectionImpl implements ClientStreamConnection {
    private static final Logger log = LoggerFactory.getLogger(DuplexStreamConnectionImpl.class);

    private final Protocol protocol;
    private final Connection connection;
    private final Proxy proxy;
    private final ActiveStreamManager activeStreamManager;

    public DuplexStreamConnectionImpl(Protocol protocol,
                                      Connection connection,
                                      Proxy proxy) {
        this.protocol = protocol;
        this.connection = connection;
        this.proxy = proxy;
        this.activeStreamManager = new ActiveStreamManager();
    }

    @Override
    public void dispatch(DecodeResult decodeResult) {
        Frame frame = decodeResult.getFrame();
        if (frame.isHeartBeat() && Protocol.supportHeartBeat(protocol)) {
            ByteBuf byteBuf = ((HeartbeatSupport) protocol).generateHeartBeatResponse(frame);
            connection.write(byteBuf);
            log.debug("[DuplexStreamConnectionImpl] 处理心跳请求完成, 已发送心跳响应");
            return;
        }

        // 决定是request还是response
        if (decodeResult.getDecodeStatus() == Decoder.DecodeStatus.COMPLETE) {
            switch (decodeResult.getStreamType()) {
                case Request:
                    handleRequest(decodeResult);
                    break;
                case Response:
                    handleResponse(decodeResult);
                    break;
                case RequestOneWay:
                    handleRequestOneWay(decodeResult);
                    break;
                default:
                    throw new IllegalStateException();

            }
        } else {
            throw new IllegalStateException("ServerStreamConnection接收到一个尚未解码的请求/响应");
        }
    }

    @Override
    public Protocol protocol() {
        return protocol;
    }

    @Override
    public ActiveStreamManager activeStreamManager() {
        return activeStreamManager;
    }

    @Override
    public void close(boolean closeRemotely) {
        final StreamResetReason reason = closeRemotely ? StreamResetReason.DownstreamConnectionCloseRemotely :StreamResetReason.DownstreamConnectionCloseLocally;
        // already create client stream
        activeStreamManager.getAllClientStreams().forEach(clientStream -> clientStream.reset(reason));
        // not ready yet
        activeStreamManager.getAllServerStreams().forEach(serverStream -> serverStream.reset(reason));

        activeStreamManager.shutdown();
    }

    private void handleRequest(DecodeResult decodeResult) {
        Frame frame = decodeResult.getFrame();
        // 创建ServerStream
        ServerStream serverStream = new ServerStream(connection, proxy, this);
        activeStreamManager.addServerStream(serverStream);
        log.debug("[DuplexStreamConnectionImpl] 开始处理请求，创建ServerStream id:{}",serverStream.getId());
        serverStream.onReceive(frame);
    }

    private void handleRequestOneWay(DecodeResult decodeResult) {
        Frame frame = decodeResult.getFrame();
        // 创建ServerStream
        ServerStream serverStream = new ServerStream(connection, proxy, this, false);
        log.debug("[DuplexStreamConnectionImpl] 开始处理OneWay请求，创建ServerStream id:{}",serverStream.getId());
        serverStream.onReceive(frame);
    }

    private void handleResponse(DecodeResult decodeResult) {
        Frame frame = decodeResult.getFrame();
        Header header = frame.getHeader();
        ClientStream clientStream = activeStreamManager.findMatchClientStream(header.getRequestId());
        log.debug("[DuplexStreamConnectionImpl] 开始处理响应，匹配ClientStream id:{}",clientStream.getId());
        clientStream.onReceive(frame);
    }

    @Override
    public ClientStream newStream(Frame frame) {
        ClientStream clientStream = new ClientStream();
        activeStreamManager.addClientStream(clientStream);
        log.debug("[] 创建新的ClientStream，id：{}", clientStream);
        return clientStream;
    }
}
