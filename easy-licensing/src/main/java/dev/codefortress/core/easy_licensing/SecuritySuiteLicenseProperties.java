package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.AutoLoadable;
import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para la licencia del módulo
 * easy-security-suite. Pueden ser cargadas desde YAML o
 * modificadas en caliente si se integra con easy-config-ui.
 */
@ConfigurationProperties(prefix = "easy.license.security-suite")
@AutoLoadable
public class SecuritySuiteLicenseProperties {

    @EasyConfigProperty(description = "Clave de activación para el módulo easy-security-suite")
    private String key = "";

    @EasyConfigProperty(description = "Marca temporal del inicio del modo trial (en ms desde epoch)")
    private long trialStartTimestamp = 0;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTrialStartTimestamp() {
        return trialStartTimestamp;
    }

    public void setTrialStartTimestamp(long trialStartTimestamp) {
        this.trialStartTimestamp = trialStartTimestamp;
    }

    /**
     * Devuelve el identificador del producto asociado.
     */
    public String getProduct() {
        return "security-suite";
    }
}
