package io.yhheng.superproxy.protocol;

public interface Protocol {
    Decoder getDecoder();

    Encoder getEncoder();
}
