package org.example.login.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.user")
public class UserProperties {
    private String dataFile;
    private int passwordMinLength;
    private String usernamePattern;

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public int getPasswordMinLength() {
        return passwordMinLength;
    }

    public void setPasswordMinLength(int passwordMinLength) {
        this.passwordMinLength = passwordMinLength;
    }

    public String getUsernamePattern() {
        return usernamePattern;
    }

    public void setUsernamePattern(String usernamePattern) {
        this.usernamePattern = usernamePattern;
    }
}