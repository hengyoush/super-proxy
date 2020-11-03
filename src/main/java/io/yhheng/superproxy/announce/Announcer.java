package io.yhheng.superproxy.announce;

public interface Announcer<E extends AnnouncerEntry> {
    void announce(E entry);
    void start();
    void shutdown();
}
