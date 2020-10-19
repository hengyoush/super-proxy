package io.yhheng.superproxy.config;

import java.util.List;
import java.util.Map;

/**
 * match : {
 *     header: {
 *         interface:
 *     }
 * }
 */
public class RouteConfig {
    private String name;
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
        private RouteMatchConfig routeMatchConfig;
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
    }

    private static class RouteMatchConfig {
        private String type;
        private Map<String, Object> typedConfig;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map<String, Object> getTypedConfig() {
            return typedConfig;
        }

        public void setTypedConfig(Map<String, Object> typedConfig) {
            this.typedConfig = typedConfig;
        }
    }

    private static class RouteActionConfig {
        private List<String> clusterNames;
        private String upstreamProtocolName;
        private Map<String, String> headerRewriteMap;
        private String lbType;

        public List<String> getClusterNames() {
            return clusterNames;
        }

        public void setClusterNames(List<String> clusterNames) {
            this.clusterNames = clusterNames;
        }

        public String getUpstreamProtocolName() {
            return upstreamProtocolName;
        }

        public void setUpstreamProtocolName(String upstreamProtocolName) {
            this.upstreamProtocolName = upstreamProtocolName;
        }

        public Map<String, String> getHeaderRewriteMap() {
            return headerRewriteMap;
        }

        public void setHeaderRewriteMap(Map<String, String> headerRewriteMap) {
            this.headerRewriteMap = headerRewriteMap;
        }

        public String getLbType() {
            return lbType;
        }

        public void setLbType(String lbType) {
            this.lbType = lbType;
        }
    }
}
