package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;
import java.util.Map;

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

    private static class RouteEntry {
        @JSONField(name = "type")
        private String type;
        @JSONField(name = "match")
        private RouteMatchConfig routeMatchConfig;
        @JSONField(name = "action")
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
    }

    private static class RouteMatchConfig {
        @JSONField(name = "typed_config")
        private Map<String, Object> typedConfig;

        public Map<String, Object> getTypedConfig() {
            return typedConfig;
        }

        public void setTypedConfig(Map<String, Object> typedConfig) {
            this.typedConfig = typedConfig;
        }
    }

    private static class RouteActionConfig {
        @JSONField(name = "cluster_name")
        private String clusterName;
        @JSONField(name = "upstream_protocol")
        private String upstreamProtocolName;
        @JSONField(name = "typed_config")
        private Map<String, Object> typedConfig;

        public String getUpstreamProtocolName() {
            return upstreamProtocolName;
        }

        public void setUpstreamProtocolName(String upstreamProtocolName) {
            this.upstreamProtocolName = upstreamProtocolName;
        }


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
    }
}
