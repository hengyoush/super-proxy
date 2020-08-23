package io.yhheng.superproxy.io;

public class Bytes {
    public static int bytes2int(byte[] b) {
        return bytes2int(b, 0);
    }

    public static int bytes2int(byte[] b, int off) {
        return ((b[off + 3] & 0xFF) << 0) +
                ((b[off + 2] & 0xFF) << 8) +
                ((b[off + 1] & 0xFF) << 16) +
                ((b[off + 0]) << 24);
    }
}
