package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import dev.codefortress.core.easy_config_ui.AutoLoadable;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para licencias por módulo.
 * Se cargan automáticamente desde application.properties o YAML.
 */
@ConfigurationProperties(prefix = "easy.license")
@AutoLoadable
public class ModuleLicenseProperties {

    @EasyConfigProperty(description = "Producto o módulo al que pertenece la licencia. Ej: 'security-suite'")
    private String product;

    @EasyConfigProperty(description = "Clave de licencia adquirida o generada")
    private String key;

    @EasyConfigProperty(description = "Habilita o deshabilita la validación de licencia")
    private boolean enabled = true;

    @EasyConfigProperty(description = "Habilita la validación remota contra el backend de licencias")
    private boolean remoteValidation = true;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRemoteValidation() {
        return remoteValidation;
    }

    public void setRemoteValidation(boolean remoteValidation) {
        this.remoteValidation = remoteValidation;
    }
}
