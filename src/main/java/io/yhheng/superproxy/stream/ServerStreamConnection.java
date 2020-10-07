package io.yhheng.superproxy.stream;

import io.netty.buffer.ByteBuf;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.protocol.DecodeResult;
import io.yhheng.superproxy.protocol.Decoder;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.proxy.Proxy;

import java.io.IOException;

public class ServerStreamConnection {
    private Protocol protocol;
    private Connection connection;
    private Proxy proxy;

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

    private void handleRequest(DecodeResult decodeResult) {
        // 区分是心跳还是普通请求
        Frame frame = decodeResult.getFrame();
        if (frame.getHeader().isHeartbeat()) {
            // TODO 使用protocol特定的心跳响应回复请求

        }

        // 创建downstream
        Downstream downstream = new Downstream();
        downstream.setServerConnection(connection);
        downstream.setFrame(frame);
        // TODO 提供一个单独的方法而不是使用getter
        proxy.getActiveStreams().add(downstream);
        downstream.receiveRequest();
    }
    private void handleRequestOneWay(DecodeResult decodeResult) {}
    private void handleResponse(DecodeResult decodeResult) {}
}
