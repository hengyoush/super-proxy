package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;
import io.yhheng.superproxy.announce.Announcer;
import io.yhheng.superproxy.announce.Announcers;

import java.util.List;
import java.util.Map;

public class AnnouncerConfig {
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "announce_entries")
    private List<AnnounceEntryConfig> announceEntries;
    @JSONField(name = "announce_destination")
    private AnnounceDestinationConfig announceDestinationConfig;

    public List<AnnounceEntryConfig> getAnnounceEntries() {
        return announceEntries;
    }

    public void setAnnounceEntries(List<AnnounceEntryConfig> announceEntries) {
        this.announceEntries = announceEntries;
    }

    public AnnounceDestinationConfig getAnnounceDestinationConfig() {
        return announceDestinationConfig;
    }

    public void setAnnounceDestinationConfig(AnnounceDestinationConfig announceDestinationConfig) {
        this.announceDestinationConfig = announceDestinationConfig;
    }

    public Announcer make() {
        return Announcers.INSTANCE.get(type, announceDestinationConfig.getType()).apply(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class AnnounceEntryConfig {
        @JSONField(name = "typed_config")
        private Map<String, Object> typedConfig;

        public Map<String, Object> getTypedConfig() {
            return typedConfig;
        }

        public void setTypedConfig(Map<String, Object> typedConfig) {
            this.typedConfig = typedConfig;
        }
    }

    public static class AnnounceDestinationConfig {
        @JSONField(name = "type")
        private String type;
        @JSONField(name = "typed_config")
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
}
