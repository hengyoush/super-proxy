package io.yhheng.superproxy.announce;

import io.yhheng.superproxy.config.AnnouncerConfig.AnnounceEntry;

public interface Announcer {
    AnnounceDestination destination();
    void announce(AnnounceEntry entry);
    void announceAll();
    void unAnnounce();
}
