package io.yhheng.superproxy.protocol;

public class DecodeResult {
    public static final DecodeResult NEED_MORE_INPUT = new DecodeResult();
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

    public void setResult(Object result) {
        this.result = result;
    }

    public void setDecodeStatus(Decoder.DecodeStatus decodeStatus) {
        this.decodeStatus = decodeStatus;
    }

    public void setE(Throwable e) {
        this.e = e;
    }
}

