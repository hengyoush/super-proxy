package io.yhheng.superproxy.protocol;

public interface Header {
    Object get(String key);
    boolean isHeartbeat();
}
