package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;
import java.util.Set;

public class ProxyFilterConfig {
    @JSONField(name = "bind_phases")
    private Set<String> bindPhases;
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "typed_config")
    private Map<String, Object> config;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public Set<String> getBindPhases() {
        return bindPhases;
    }

    public void setBindPhases(Set<String> bindPhases) {
        this.bindPhases = bindPhases;
    }
}
