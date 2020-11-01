package io.yhheng.superproxy.protocol.dubbo;

import io.yhheng.superproxy.protocol.Frame;

public class DubboFrame extends Frame {

    @Override
    public boolean isHeartBeat() {
        return ((DubboHeader) getHeader()).isEvent() && getData() == null;
    }
}
