package io.yhheng.superproxy.common.utils;

import java.util.Map;

public class TypedConfig {
    private Map<String, Object> wrapped;

    public TypedConfig(Map<String, Object> wrapped) {
        this.wrapped = wrapped;
    }

    public static TypedConfig wrap(Map<String, Object> wrapped) {
        return new TypedConfig(wrapped);
    }

    public String getString(String key, boolean require) {
        String s = String.valueOf(wrapped.get(key));
        if (s == null && require) {
            throw new IllegalArgumentException(String.format("%s is required, but is null!", key));
        }
        return s;
    }

    public String getString(String key) {
        return getString(key, false);
    }
}
