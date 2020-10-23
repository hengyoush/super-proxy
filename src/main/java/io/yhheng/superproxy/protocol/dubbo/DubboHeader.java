package io.yhheng.superproxy.protocol.dubbo;

import io.yhheng.superproxy.protocol.Header;

public class DubboHeader implements Header {
    private boolean isReq;
    private boolean isEvent;
    private boolean twoway;
    private byte status;
    private int serializerId;
    private long requestId;
    // in the body
    private String dubboVersion;
    private String interfaceName;
    private String version;
    private String group;

    public String getDubboVersion() {
        return dubboVersion;
    }

    public void setDubboVersion(String dubboVersion) {
        this.dubboVersion = dubboVersion;
    }

    public boolean isReq() {
        return isReq;
    }

    public void setReq(boolean req) {
        isReq = req;
    }

    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean event) {
        isEvent = event;
    }

    public boolean isTwoway() {
        return twoway;
    }

    public void setTwoway(boolean twoway) {
        this.twoway = twoway;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getSerializerId() {
        return serializerId;
    }

    public void setSerializerId(int serializerId) {
        this.serializerId = serializerId;
    }

    @Override
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    // TODO key in (...)
    @Override
    public Object get(String key) {
        return null;
    }
}
