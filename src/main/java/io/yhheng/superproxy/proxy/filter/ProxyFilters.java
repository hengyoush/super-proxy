package io.yhheng.superproxy.proxy.filter;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;
import io.yhheng.superproxy.stream.StreamPhase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ProxyFilters extends AbstractObjectRegistry<ProxyFilter> {
    public static final ProxyFilters INSTANCE = new ProxyFilters();
    private final Map<StreamPhase, List<ProxyFilter>> proxyFilterTable = new ConcurrentHashMap<>();
    @Override
    public void register(String type, ProxyFilter proxyFilter) {
        super.register(type, proxyFilter);
        for (StreamPhase phase : proxyFilter.phases()) {
            proxyFilterTable.computeIfAbsent(phase, i -> new ArrayList<>()).add(proxyFilter);
        }
    }

    public List<ProxyFilter> getFilters(StreamPhase phase) {
        return Optional.ofNullable(proxyFilterTable.get(phase)).orElse(Collections.emptyList());
    }

    @Override
    protected String desc() {
        return "ProxyFilter";
    }
}
