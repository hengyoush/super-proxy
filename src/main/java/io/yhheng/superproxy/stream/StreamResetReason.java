package io.yhheng.superproxy.stream;

public enum StreamResetReason {
    DownstreamConnectionCloseRemotely, DownstreamConnectionCloseLocally,
    UpstreamConnectionTimeout, UpstreamConnectionCloseRemotely;

    public boolean isDownstreamClose() {
        if (this == DownstreamConnectionCloseLocally || this == DownstreamConnectionCloseRemotely) {
            return true;
        }
        return false;
    }

    // TODO
    public boolean isUpstream() {
        return false;
    }
}
