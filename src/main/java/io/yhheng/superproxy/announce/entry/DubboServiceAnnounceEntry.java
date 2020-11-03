package io.yhheng.superproxy.announce.entry;

import io.yhheng.superproxy.announce.AnnouncerEntry;
import io.yhheng.superproxy.config.AnnouncerConfig;
import io.yhheng.superproxy.network.ActiveListeners;
import io.yhheng.superproxy.network.Listener;

import java.util.Map;
import java.util.Objects;

public class DubboServiceAnnounceEntry implements AnnouncerEntry {
    private String interfaceName;
    private String group;
    private String version;
    private String host;
    private Integer port;

    public static DubboServiceAnnounceEntry of(AnnouncerConfig.AnnounceEntryConfig config) {
        return (DubboServiceAnnounceEntry) new DubboServiceAnnounceEntry().fromConfig(config);
    }

    @Override
    public AnnouncerEntry fromConfig(AnnouncerConfig.AnnounceEntryConfig config) {
        Map<String, Object> typedConfig = config.getTypedConfig();
        this.interfaceName = Objects.requireNonNull((String) typedConfig.get("interface_name"));
        this.group = (String) typedConfig.get("group");
        this.version = (String) typedConfig.get("version");
        Listener listener = ActiveListeners.INSTANCE.get((String) typedConfig.get("listener_ref"));
        if (listener != null) {
            this.host = listener.bindAddr().getHostString();
            this.port = listener.bindAddr().getPort();
        }
        return this;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
