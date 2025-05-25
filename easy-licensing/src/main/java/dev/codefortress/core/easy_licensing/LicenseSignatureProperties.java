package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import dev.codefortress.core.easy_config_ui.AutoLoadable;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades para validar la firma digital del token de licencia.
 */
@ConfigurationProperties(prefix = "easy.license.signature")
@AutoLoadable
public class LicenseSignatureProperties {

    @EasyConfigProperty(description = "Secreto compartido para verificar la firma del token de licencia")
    private String secret = "default-signature-secret";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
