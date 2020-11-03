package io.yhheng.superproxy.announce;

import io.yhheng.superproxy.config.AnnouncerConfig;

public interface AnnouncerEntry {
    AnnouncerEntry fromConfig(AnnouncerConfig.AnnounceEntryConfig config);
}
