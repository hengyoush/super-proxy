package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;
import io.yhheng.superproxy.announce.Announcer;

import java.util.List;
import java.util.Map;

public class AnnouncerConfig {
    @JSONField(name = "announce_entries")
    private List<AnnounceEntry> announceEntries;
    @JSONField(name = "announce_destination")
    private AnnounceDestinationConfig announceDestinationConfig;

    public List<AnnounceEntry> getAnnounceEntries() {
        return announceEntries;
    }

    public void setAnnounceEntries(List<AnnounceEntry> announceEntries) {
        this.announceEntries = announceEntries;
    }

    public AnnounceDestinationConfig getAnnounceDestinationConfig() {
        return announceDestinationConfig;
    }

    public void setAnnounceDestinationConfig(AnnounceDestinationConfig announceDestinationConfig) {
        this.announceDestinationConfig = announceDestinationConfig;
    }

    public Announcer make() {
        return null;
    }

    public static class AnnounceEntry {
        private String type;
        private String listenerRef;
        private Map<String, Object> typedConfig;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getListenerRef() {
            return listenerRef;
        }

        public void setListenerRef(String listenerRef) {
            this.listenerRef = listenerRef;
        }

        public Map<String, Object> getTypedConfig() {
            return typedConfig;
        }

        public void setTypedConfig(Map<String, Object> typedConfig) {
            this.typedConfig = typedConfig;
        }
    }

    public static class AnnounceDestinationConfig {
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
}
