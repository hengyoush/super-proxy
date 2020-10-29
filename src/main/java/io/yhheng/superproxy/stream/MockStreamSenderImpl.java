package io.yhheng.superproxy.stream;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockStreamSenderImpl implements StreamSender {
    private static final Logger log = LoggerFactory.getLogger(MockStreamSenderImpl.class);

    @Override
    public void send(UpstreamRequest upstreamRequest) {
        log.error("upstreamRequest:{}已发送！", JSON.toJSONString(upstreamRequest));
        // 接收响应
    }
}
