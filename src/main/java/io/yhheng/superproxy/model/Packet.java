package io.yhheng.superproxy.model;

public class Packet {
    private byte[] rawHeader;
    private byte[] rawBody;

    public Packet(byte[] rawHeader, byte[] rawBody) {
        this.rawHeader = rawHeader;
        this.rawBody = rawBody;
    }
}
