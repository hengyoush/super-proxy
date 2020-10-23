package io.yhheng.superproxy.protocol.dubbo;

import io.yhheng.superproxy.protocol.Frame;

public class DubboFrame extends Frame {

    @Override
    protected boolean isHeartBeat() {
        return ((DubboHeader) getHeader()).isEvent() && getData() == null;
    }
}
