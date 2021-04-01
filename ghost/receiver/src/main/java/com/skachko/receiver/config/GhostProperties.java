package com.skachko.receiver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties("ghost.receiver")
public class GhostProperties {
    private boolean enabled;
    private Map<String, Map<String,String>> essences;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, Map<String, String>> getEssences() {
        return essences;
    }

    public void setEssences(Map<String, Map<String, String>> essences) {
        this.essences = essences;
    }
}
