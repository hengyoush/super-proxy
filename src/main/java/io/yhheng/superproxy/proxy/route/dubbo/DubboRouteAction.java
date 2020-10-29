package io.yhheng.superproxy.proxy.route.dubbo;

import com.alibaba.fastjson.annotation.JSONField;
import io.yhheng.superproxy.protocol.Frame;
import io.yhheng.superproxy.protocol.Header;
import io.yhheng.superproxy.protocol.Protocol;
import io.yhheng.superproxy.protocol.dubbo.DubboHeader;
import io.yhheng.superproxy.proxy.route.RouteActionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.yhheng.superproxy.proxy.route.dubbo.DubboRouteAction.RewriteConfig.NO_OP;

public class DubboRouteAction extends RouteActionImpl {
    private static final Logger log = LoggerFactory.getLogger(DubboRouteAction.class);

    private RewriteConfig rewriteConfig;
    public DubboRouteAction(Protocol upstreamProtocol, String upstreamClusterName, RewriteConfig rewriteConfig) {
        super(upstreamProtocol, upstreamClusterName);
        this.rewriteConfig = rewriteConfig;
    }

    @Override
    public void processFrame(Frame frame) {
        rewrite(frame.getHeader());
    }

    private void rewrite(Header header) {
        assert header instanceof DubboHeader;
        DubboHeader dubboHeader = (DubboHeader) header;
        String rewriteGroup = rewriteConfig.getRewriteGroup();
        String rewriteVersion = rewriteConfig.getRewriteVersion();
        if (!NO_OP.equals(rewriteGroup)) {
            log.info("rewrite dubbo header group to: {}, origin group is {}", rewriteGroup, dubboHeader.getGroup());
            dubboHeader.setGroup(rewriteGroup);
        }

        if (!NO_OP.equals(rewriteVersion)) {
            log.info("rewrite dubbo header version to: {}, origin version is {}", rewriteVersion, dubboHeader.getVersion());
            dubboHeader.setVersion(rewriteVersion);
        }

    }

    public static class RewriteConfig {
        static final String NO_OP = "\\/";
        @JSONField(name = "rewrite_group")
        private String rewriteGroup;
        @JSONField(name = "rewrite_version")
        private String rewriteVersion;

        public String getRewriteGroup() {
            return rewriteGroup;
        }

        public void setRewriteGroup(String rewriteGroup) {
            this.rewriteGroup = rewriteGroup;
        }

        public String getRewriteVersion() {
            return rewriteVersion;
        }

        public void setRewriteVersion(String rewriteVersion) {
            this.rewriteVersion = rewriteVersion;
        }
    }
}
