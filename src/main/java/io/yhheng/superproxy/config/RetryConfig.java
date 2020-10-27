package io.yhheng.superproxy.config;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

public class RetryConfig {
    @JSONField(name = "retry_on")
    private boolean retryOn;
    @JSONField(name = "num_retries")
    private int numRetries;
    @JSONField(name = "retry_timeout")
    private Long retryTimeout;
    @JSONField(name = "backoff")
    private BackoffConfig backoffConfig;

    public boolean isRetryOn() {
        return retryOn;
    }

    public void setRetryOn(boolean retryOn) {
        this.retryOn = retryOn;
    }

    public int getNumRetries() {
        return numRetries;
    }

    public void setNumRetries(int numRetries) {
        this.numRetries = numRetries;
    }

    public Long getRetryTimeout() {
        return retryTimeout;
    }

    public void setRetryTimeout(Long retryTimeout) {
        this.retryTimeout = retryTimeout;
    }

    public BackoffConfig getBackoffConfig() {
        return backoffConfig;
    }

    public void setBackoffConfig(BackoffConfig backoffConfig) {
        this.backoffConfig = backoffConfig;
    }

    public static class BackoffConfig {
        private String type;
        private Map<String, Object> typedConfig;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map<String, Object> getTypedConfig() {
            return typedConfig;
        }

        public void setTypedConfig(Map<String, Object> typedConfig) {
            this.typedConfig = typedConfig;
        }
    }
}
