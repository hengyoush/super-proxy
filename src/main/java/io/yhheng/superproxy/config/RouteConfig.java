package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;
import io.yhheng.superproxy.common.utils.Validate;
import io.yhheng.superproxy.proxy.route.Route;
import io.yhheng.superproxy.proxy.route.RouteFactories;
import io.yhheng.superproxy.proxy.route.RouterTable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RouteConfig {
    @JSONField(name = "router_table_name")
    private String name;
    @JSONField(name = "route_table_entries")
    private List<RouteEntry> routeEntries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RouteEntry> getRouteEntries() {
        return routeEntries;
    }

    public void setRouteEntries(List<RouteEntry> routeEntries) {
        this.routeEntries = routeEntries;
    }

    public RouterTable make() {
        Validate.assertNotEmpty(name, "RouteName");
        Validate.assertNotEmpty(routeEntries, "routeEntries");
        List<Route> routes = routeEntries.stream().map(RouteEntry::make).collect(Collectors.toList());
        return new RouterTable(name, routes);
    }

    public static class RouteEntry {
        @JSONField(name = "type")
        private String type;
        @JSONField(name = "route_match")
        private RouteMatchConfig routeMatchConfig;
        @JSONField(name = "route_action")
        private RouteActionConfig routeActionConfig;

        public RouteMatchConfig getRouteMatchConfig() {
            return routeMatchConfig;
        }

        public void setRouteMatchConfig(RouteMatchConfig routeMatchConfig) {
            this.routeMatchConfig = routeMatchConfig;
        }

        public RouteActionConfig getRouteActionConfig() {
            return routeActionConfig;
        }

        public void setRouteActionConfig(RouteActionConfig routeActionConfig) {
            this.routeActionConfig = routeActionConfig;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Route make() {
            Validate.assertNotEmpty(type, "RouteEntryType");
            Route.Factory routeFactory = RouteFactories.INSTANCE.get(type);
            return routeFactory.create(this);
        }
    }

    public static class RouteMatchConfig {
        @JSONField(name = "typed_config")
        private Map<String, Object> typedConfig;

        public Map<String, Object> getTypedConfig() {
            return typedConfig;
        }

        public void setTypedConfig(Map<String, Object> typedConfig) {
            this.typedConfig = typedConfig;
        }
    }

    public static class RouteActionConfig {
        @JSONField(name = "cluster_name")
        private String clusterName;
        @JSONField(name = "upstream_protocol")
        private ProtocolConfig upstreamProtocol;
        @JSONField(name = "typed_config")
        private Map<String, Object> typedConfig;


        public Map<String, Object> getTypedConfig() {
            return typedConfig;
        }

        public void setTypedConfig(Map<String, Object> typedConfig) {
            this.typedConfig = typedConfig;
        }

        public String getClusterName() {
            return clusterName;
        }

        public void setClusterName(String clusterName) {
            this.clusterName = clusterName;
        }

        public ProtocolConfig getUpstreamProtocol() {
            return upstreamProtocol;
        }

        public void setUpstreamProtocol(ProtocolConfig upstreamProtocol) {
            this.upstreamProtocol = upstreamProtocol;
        }
    }
}
