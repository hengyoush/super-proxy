package io.yhheng.superproxy.stream;

import io.yhheng.superproxy.cluster.Host;
import io.yhheng.superproxy.network.Connection;
import io.yhheng.superproxy.network.ConnectionPool;
import io.yhheng.superproxy.protocol.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamSenderImpl implements StreamSender {
    private static final Logger log = LoggerFactory.getLogger(StreamSenderImpl.class);
    @Override
    public void send(UpstreamRequest upstreamRequest) {
        Frame frame = upstreamRequest.getFrame();
        Host host = upstreamRequest.getHost();
        ConnectionPool connPool = host.getConnPool();
        Connection connection = connPool.getConnection(frame.getHeader());
        connection.write(frame.getRawBuf());
    }
}
