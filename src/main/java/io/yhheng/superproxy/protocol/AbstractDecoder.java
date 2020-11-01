package io.yhheng.superproxy.protocol;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public abstract class AbstractDecoder implements Decoder {
    @Override
    public DecodeResult decode(ByteBuf byteBuf) {
        if (protocol().isAssignableFrom(LengthFieldSupport.class)) {
            DecodeResult decodeResult = tryDecode(byteBuf); // 总是成功
            if (decodeResult.getDecodeStatus() == DecodeStatus.NEED_MORE_INPUT) {
                throw new IllegalStateException("无效的解码");
            }
            return decodeResult;
        } else {
            int savedIndex = byteBuf.readerIndex();

            DecodeResult decodeResult = tryDecode(byteBuf);

            DecodeStatus decodeStatus = decodeResult.getDecodeStatus();
            if (decodeStatus == DecodeStatus.NEED_MORE_INPUT) {
                byteBuf.readerIndex(savedIndex);
                return DecodeResult.NEED_MORE_INPUT;
            } else if (decodeStatus == DecodeStatus.COMPLETE) {
                return decodeResult;
            } else {
                throw new IllegalStateException("can't reach here");
            }
        }
    }

    /**
     * 这个方法的byteBuf可能没有包含足够解码的内容
     * @param byteBuf 字节数组
     * @return 解码结果
     */
    public abstract DecodeResult tryDecode(ByteBuf byteBuf);

    /**
     * 这个方法的byteBuf可以确定包含了一个足够的解码内容
     * @param byteBuf 字节数组
     * @return 解码结果，必须确保是COMPLETE
     */
    public abstract DecodeResult doDecode(ByteBuf byteBuf);

    public abstract Class<? extends Protocol> protocol();
}
