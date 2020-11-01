package io.yhheng.superproxy.protocol;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;
import io.yhheng.superproxy.config.ProtocolConfig;
import io.yhheng.superproxy.protocol.dubbo.DubboProtocol;
import io.yhheng.superproxy.protocol.dubbo.DubboProtocolConstants;

import java.util.Optional;
import java.util.function.Function;

public class Protocols extends AbstractObjectRegistry<Function<ProtocolConfig, Protocol>> {
    public static final Protocols INSTANCE = new Protocols();

    static {
        INSTANCE.register(DubboProtocolConstants.PROTOCOL_NAME, DubboProtocol.factory());
    }

    public Protocol newProtocol(ProtocolConfig protocolConfig) {
        Function<ProtocolConfig, Protocol> fun = INSTANCE.get(protocolConfig.getType());
        return Optional.ofNullable(fun).map(f -> f.apply(protocolConfig)).orElse(null);
    }

    @Override
    protected String desc() {
        return "protocol";
    }
}
