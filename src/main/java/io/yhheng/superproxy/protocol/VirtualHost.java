package io.yhheng.superproxy.protocol;

public interface VirtualHost {
    boolean match(Header header);
}
