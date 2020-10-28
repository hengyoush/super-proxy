package io.yhheng.superproxy.common.utils;

import org.apache.dubbo.common.utils.StringUtils;

import java.util.Collection;

public class Validate {
    public static void assertNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(String.format("%s can't be null!", name));
        }
    }

    public static void assertNotEmpty(Collection<?> collection, String name) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(String.format("collection: %s can't be empty!", name));
        }
    }

    public static void assertNotEmpty(String str, String name) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(String.format("string: %s can't be empty!", name));
        }
    }
}
