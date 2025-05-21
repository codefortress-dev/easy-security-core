package dev.codefortress.core.easy_captcha;

import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import dev.codefortress.core.easy_config_ui.AutoLoadable;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Propiedades configurables para el sistema de validación anti-bot.
 * Permite proteger endpoints sensibles sin requerir captcha visual.
 */
@ConfigurationProperties(prefix = "easy.captcha")
@AutoLoadable
public class CaptchaProperties {

    @EasyConfigProperty(description = "Habilita o deshabilita la protección captcha.")
    private boolean enabled = false;

    @EasyConfigProperty(description = "Tiempo mínimo esperado para enviar un formulario (ms).")
    private long minResponseTimeMillis = 1000;

    @EasyConfigProperty(description = "Activa validación honeypot (campo oculto)")
    private boolean honeypotEnabled = true;

    @EasyConfigProperty(description = "Nombre del campo honeypot en formularios.")
    private String honeypotField = "email_confirm";

    @EasyConfigProperty(description = "Mensaje mostrado si se bloquea por comportamiento automático.")
    private String blockMessage = "Parece comportamiento automático. Intenta nuevamente.";

    @EasyConfigProperty(description = "Rutas protegidas por el captcha silencioso.")
    private List<String> protectedPaths = List.of("/login", "/register");

    // Getters y Setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public long getMinResponseTimeMillis() { return minResponseTimeMillis; }
    public void setMinResponseTimeMillis(long minResponseTimeMillis) { this.minResponseTimeMillis = minResponseTimeMillis; }

    public boolean isHoneypotEnabled() { return honeypotEnabled; }
    
    public void setHoneypotEnabled(boolean honeypotEnabled) { this.honeypotEnabled = honeypotEnabled; }

    public String getHoneypotField() { return honeypotField; }
    public void setHoneypotField(String honeypotField) { this.honeypotField = honeypotField; }

    public String getBlockMessage() { return blockMessage; }
    public void setBlockMessage(String blockMessage) { this.blockMessage = blockMessage; }

    public List<String> getProtectedPaths() { return protectedPaths; }
    public void setProtectedPaths(List<String> protectedPaths) { this.protectedPaths = protectedPaths; }
}
