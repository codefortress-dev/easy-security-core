package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.AutoLoadable;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "easy.license.signature")
@AutoLoadable
public class LicenseSignatureProperties {

    private String secret = "codefortress-default-secret";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
