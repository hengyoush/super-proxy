package io.yhheng.superproxy.filter;

import io.yhheng.superproxy.model.Upstream;

public interface WriteFilter {
    void onWrite(Upstream upstream);
}
