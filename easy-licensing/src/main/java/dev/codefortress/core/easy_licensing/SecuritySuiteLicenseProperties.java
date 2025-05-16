package dev.codefortress.core.easy_licensing;
import org.springframework.boot.context.properties.ConfigurationProperties;
import dev.codefortress.core.easy_config_ui.AutoLoadable;
import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
@ConfigurationProperties(prefix = "easy.license.security-suite")
@AutoLoadable
public class SecuritySuiteLicenseProperties {
    @EasyConfigProperty(description = "Clave de activación para security-suite")
    private String key = "";
    private long trialStartTimestamp;

    
}