package io.yhheng.superproxy.stream;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Header;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.Proxy;

import java.io.IOException;

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
    public void dispatch(ByteBuf byteBuf) {
        // 决定是request还是response
        try {
            DecodeResult decode = protocol.getDecoder().decode(byteBuf);
            if (decode.getDecodeStatus() == Decoder.DecodeStatus.COMPLETE) {
                switch (decode.getStreamType()) {
                    case Request:
                        handleRequest(decode);
                        break;
                    case Response:
                        handleResponse(decode);
                        break;
                    case RequestOneWay:
                        handleRequestOneWay(decode);
                        break;
                    default:
                        throw new IllegalStateException();

                }
            } else {
                // 没有足够数据输入,直接返回
                // 需要注意不要修改byteBuf的readerIndex,或许该在外面保存？
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        if (frame.getHeader().isHeartbeat()) {
            // TODO 使用protocol特定的心跳响应回复请求

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
