package io.yhheng.superproxy.filter;

import io.yhheng.superproxy.model.Downstream;

public interface ReadFilter {
    FilterStatus onRead(Downstream downstream);
}
