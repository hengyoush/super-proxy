package io.yhheng.superproxy.common.utils;

public abstract class TwoTypeSupportObjectRegistry<T> extends AbstractObjectRegistry<T> {
    public T get(String type1, String type2) {
        return get(type1 + "$" + type2);
    }

    public void register(String type1, String type2, T t) {
        register(type1 + "$" + type2, t);
    }
}
