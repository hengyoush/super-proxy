package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;
import io.yhheng.superproxy.stream.Stream;

import java.util.Map;

public class ConnectionEventListenerConfig {
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "typed_config")
    private Map<String, Object> typedConfig;

    public Map<String, Object> getTypedConfig() {
        return typedConfig;
    }

    public void setTypedConfig(Map<String, Object> typedConfig) {
        this.typedConfig = typedConfig;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
