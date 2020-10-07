package io.yhheng.superproxy.protocol.dubbo;

import io.yhheng.superproxy.protocol.Header;

public class DubboHeader implements Header {
    private String frameworkVersion;
    private String serviceName;
    private String group;
    private String version;
    private boolean isEvent;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean event) {
        isEvent = event;
    }

    public String getFrameworkVersion() {
        return frameworkVersion;
    }

    public void setFrameworkVersion(String frameworkVersion) {
        this.frameworkVersion = frameworkVersion;
    }

    // TODO key in (...)
    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public boolean isHeartbeat() {
        return isEvent;
    }
}
