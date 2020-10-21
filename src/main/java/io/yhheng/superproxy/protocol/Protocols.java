package io.yhheng.superproxy.protocol;

import io.yhheng.superproxy.common.utils.AbstractObjectRegistry;

public class Protocols extends AbstractObjectRegistry<Protocol> {
    public static final Protocols INSTANCE = new Protocols();

    @Override
    protected String desc() {
        return "protocol";
    }
}
