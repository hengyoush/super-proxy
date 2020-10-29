package io.yhheng.superproxy.proxy.route.dubbo;

import io.yhheng.superproxy.protocol.Header;
import io.yhheng.superproxy.protocol.dubbo.DubboHeader;
import io.yhheng.superproxy.proxy.route.RouteMatch;

import java.util.Objects;

public class DubboRouteMatch implements RouteMatch {
    private String interfaceName;
    private String group;
    private String version;

    public DubboRouteMatch(String interfaceName, String group, String version) {
        this.interfaceName = interfaceName;
        this.group = group;
        this.version = version;
    }

    @Override
    public boolean match(Header header) {
        if (header instanceof DubboHeader) {
            DubboHeader dubboHeader = (DubboHeader) header;
            return Objects.equals(dubboHeader.getInterfaceName(), this.interfaceName) &&
                    Objects.equals(dubboHeader.getGroup(), this.group) &&
                    Objects.equals(dubboHeader.getVersion(), this.version);
        }
        return false;
    }
}
