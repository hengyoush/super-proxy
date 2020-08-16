package io.yhheng.superproxy.protocol;

import io.yhheng.superproxy.model.DownstreamRequest;
import io.yhheng.superproxy.model.Packet;

public interface Protocol {
    DownstreamRequest decode(Packet packet);
}
