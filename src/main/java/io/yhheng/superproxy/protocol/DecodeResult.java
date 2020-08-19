package io.yhheng.superproxy.protocol;

public class DecodeResult {
    private Object result;
    private Decoder.DecodeStatus decodeStatus;
    private Throwable e;

    public Object getResult() {
        return result;
    }

    public Decoder.DecodeStatus getDecodeStatus() {
        return decodeStatus;
    }

    public Throwable getException() {
        return e;
    }
}

