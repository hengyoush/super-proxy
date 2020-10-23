package io.yhheng.superproxy.protocol;

public interface Protocol {
    Decoder getDecoder();

    Encoder getEncoder();

    static boolean supportHeartBeat(Protocol protocol) {
        return protocol instanceof HeartbeatSupport;
    }
}
