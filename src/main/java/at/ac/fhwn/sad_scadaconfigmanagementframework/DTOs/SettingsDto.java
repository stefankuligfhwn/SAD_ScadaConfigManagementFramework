package at.ac.fhwn.sad_scadaconfigmanagementframework.DTOs;

import java.util.Map;

public class SettingsDto {
    private Map<String, Object> settings;

    public Map<String, Object> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }
}