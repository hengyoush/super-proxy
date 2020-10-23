package io.yhheng.superproxy.protocol.dubbo;

import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.IOException;

public class HessianUtils {
    public static ByteBuf writeNull() {
        ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(Unpooled.buffer());
        Hessian2ObjectOutput hessian2ObjectOutput = new Hessian2ObjectOutput(byteBufOutputStream);
        try {
            hessian2ObjectOutput.writeObject(null);
            return byteBufOutputStream.buffer();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
