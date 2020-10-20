package io.yhheng.superproxy.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractObjectRegistry<T> {
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private Map<String, T> REGISTRY_TABLE = new ConcurrentHashMap<>();

    public void register(String type, T t) {
        T oldValue = REGISTRY_TABLE.putIfAbsent(type, t);
        if (oldValue != null) {
            log.warn("type: {} 的{}: {}已经存在，尝试定义重复type的{}:{}", type, desc(), oldValue.getClass(), desc(), t.getClass());
        }
    }

    public T get(String type) {
        return REGISTRY_TABLE.get(type);
    }

    protected abstract String desc();
}
