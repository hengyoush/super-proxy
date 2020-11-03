package io.yhheng.superproxy.announce.dest;

import io.yhheng.superproxy.config.AnnouncerConfig;

import java.util.Map;

public class ZookeeperAnnounceDestination {
    private String address;
    private String path;

    public ZookeeperAnnounceDestination(AnnouncerConfig.AnnounceDestinationConfig config) {
        Map<String, Object> typedConfig = config.getTypedConfig();
        this.address = (String) typedConfig.get("address");
        this.path = (String) typedConfig.get("path");
    }

    public String getAddress() {
        return address;
    }

    public String getPrefixPath() {
        return path;
    }
}
