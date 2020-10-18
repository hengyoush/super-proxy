package io.yhheng.superproxy.stream;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Header;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.Proxy;
import io.yhheng.superproxy.proxy.filter.StreamFilterManager;

import java.io.IOException;

public class ServerStreamConnection implements StreamConnection {
    private Protocol protocol;
    private Connection connection;
    private Proxy proxy;
    private ActiveStreamManager activeStreamManager;
    private StreamFilterManager streamFilterManager;

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

    private void handleRequest(DecodeResult decodeResult) {
        // 区分是心跳还是普通请求
        Frame frame = decodeResult.getFrame();
        if (frame.getHeader().isHeartbeat()) {
            // TODO 使用protocol特定的心跳响应回复请求

        }

        // 创建downstream
        ServerStream serverStream = new ServerStream();
        serverStream.setServerConnection(connection);
        serverStream.setFrame(frame);
        // TODO 提供一个单独的方法而不是使用getter
        proxy.getActiveStreamManager().addServerStream(serverStream);
        serverStream.onReceive(frame);
        UpstreamRequest upstreamRequest = serverStream.getUpstreamRequest();
        Connection connection = proxy.getClusterManager().initialzeConnectionForHost(serverStream.getUpstreamHost());

        // TODO 设置future 等待上游返回响应


        ByteBuf byteBuf = protocol.getEncoder().encode(upstreamRequest.getFrame());
        connection.write(byteBuf);
    }
    private void handleRequestOneWay(DecodeResult decodeResult) {}
    private void handleResponse(DecodeResult decodeResult) {
        Frame frame = decodeResult.getFrame();
        Header header = frame.getHeader();
        ClientStream clientStream = activeStreamManager.findMatchClientStream(header.getRequestId());
        clientStream.onReceive(frame);
    }
}
