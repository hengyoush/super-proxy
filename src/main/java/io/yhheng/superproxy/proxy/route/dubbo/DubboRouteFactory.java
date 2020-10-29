package io.yhheng.superproxy.proxy.route.dubbo;

import com.alibaba.fastjson.JSONObject;
import io.yhheng.superproxy.common.utils.TypedConfig;
import io.yhheng.superproxy.config.RouteConfig;
import io.yhheng.superproxy.protocol.Protocols;
import io.yhheng.superproxy.proxy.route.Route;
import io.yhheng.superproxy.proxy.route.RouteAction;
import io.yhheng.superproxy.proxy.route.RouteMatch;

import java.util.Objects;

public enum DubboRouteFactory implements Route.Factory {
    INSTANCE;
    public static final String TYPE = DubboConstants.PROTOCOL_NAME;

    @Override
    public Route create(RouteConfig.RouteEntry routeEntry) {
        String type = routeEntry.getType();
        assert Objects.equals(type, TYPE);
        return new DubboRoute(createRouteMatch(routeEntry.getRouteMatchConfig()),
                createRouteAction(routeEntry.getRouteActionConfig()));
    }

    @Override
    public RouteMatch createRouteMatch(RouteConfig.RouteMatchConfig config) {
        TypedConfig wrap = TypedConfig.wrap(config.getTypedConfig());
        return new DubboRouteMatch(wrap.getString(DubboConstants.INTERFACE_NAME_KEY, true),
                wrap.getString(DubboConstants.GROUP_KEY),
                wrap.getString(DubboConstants.VERSION_KEY));
    }

    @Override
    public RouteAction createRouteAction(RouteConfig.RouteActionConfig routeActionConfig) {
        String clusterName = routeActionConfig.getClusterName();
        String upstreamProtocolName = routeActionConfig.getUpstreamProtocolName();
        DubboRouteAction.RewriteConfig rewriteConfig =
                new JSONObject(routeActionConfig.getTypedConfig()).toJavaObject(DubboRouteAction.RewriteConfig.class);
        return new DubboRouteAction(Protocols.INSTANCE.get(upstreamProtocolName), clusterName, rewriteConfig);
    }
}
