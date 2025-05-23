package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.AutoLoadable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
/**
 * Propiedades de licencia reutilizables para cualquier módulo comercial de CodeFortress.
 * Incluye clave, nombre del producto, y soporte para modo trial.
 */
@ConfigurationProperties(prefix = "easy.license")
@AutoLoadable
public class ModuleLicenseProperties {

    /**
     * Nombre del módulo/componente (ej: security-suite, gateway-suite, etc.)
     */
    private String product;

    /**
     * Clave de activación ingresada por el usuario.
     */
    private String key;

    /**
     * Indica si se debe validar la licencia de este módulo.
     */
    private boolean enabled = true;

    /**
     * Timestamp en milisegundos desde epoch cuando se inició el trial en producción.
     * Usado para verificar los 21 días de evaluación.
     */
    private long trialStartTimestamp = 0;
    @EasyConfigProperty(description = "Habilita o deshabilita la validación remota del servidor de licencias")
    private boolean remoteValidation = false;
    
    public boolean isRemoteValidation() { return remoteValidation; }
    public void setRemoteValidation(boolean remoteValidation) { this.remoteValidation = remoteValidation; }

    // === Getters y Setters ===

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

    public long getTrialStartTimestamp() {
        return trialStartTimestamp;
    }

    public void setTrialStartTimestamp(long trialStartTimestamp) {
        this.trialStartTimestamp = trialStartTimestamp;
    }
}
