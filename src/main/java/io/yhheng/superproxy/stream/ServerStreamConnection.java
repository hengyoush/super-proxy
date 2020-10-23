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

public class ServerStreamConnection implements StreamConnection {
    private final Protocol protocol;
    private final Connection connection;
    private final Proxy proxy;
    private final ActiveStreamManager activeStreamManager;

    public ServerStreamConnection(Protocol protocol,
                                  Connection connection,
                                  Proxy proxy) {
        this.protocol = protocol;
        this.connection = connection;
        this.proxy = proxy;
        this.activeStreamManager = new ActiveStreamManager();
    }

    @Override
    public void dispatch(DecodeResult decodeResult) {
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

    private void handleRequest(DecodeResult decodeResult) {
        // 区分是心跳还是普通请求
        Frame frame = decodeResult.getFrame();
        if (frame.isHeartBeat() && Protocol.supportHeartBeat(protocol)) {
            ByteBuf byteBuf = ((HeartbeatSupport) protocol).generateHeartBeatResponse(frame);
            connection.write(byteBuf);
            return;
        }
        // 创建ServerStream
        ServerStream serverStream = new ServerStream(connection, proxy, this);
        serverStream.onReceive(frame);
    }
    private void handleRequestOneWay(DecodeResult decodeResult) {}
    private void handleResponse(DecodeResult decodeResult) {
        Frame frame = decodeResult.getFrame();
        Header header = frame.getHeader();
        ClientStream clientStream = activeStreamManager.findMatchClientStream(header.getRequestId());
        clientStream.onReceive(frame);
    }
}
