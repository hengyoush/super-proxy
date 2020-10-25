package io.yhheng.superproxy.stream;

public enum StreamResetReason {
    CloseRemotely, CloseLocally;

    public boolean isDownstreamClose() {
        if (this == CloseLocally || this == CloseRemotely) {
            return true;
        }
        return false;
    }
}
