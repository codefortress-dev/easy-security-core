package dev.codefortress.core.easy_security_core_jwt;

import dev.codefortress.core.easy_config_ui.AutoLoadable;
import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades configurables para la generación y validación de JWT.
 * Estas configuraciones pueden ser definidas por YAML o modificadas en caliente
 * si se integra con la interfaz visual del módulo easy-config-ui.
 */
@ConfigurationProperties(prefix = "easy.jwt")
@AutoLoadable
public class JwtProperties {

    @EasyConfigProperty(description = "Clave secreta para firmar y verificar JWTs (HS256)")
    private String secretKey = "default-secret";

    @EasyConfigProperty(description = "Tiempo de expiración del token en segundos")
    private long expirationSeconds = 3600;

    /**
     * Retorna la clave secreta usada para firmar/verificar JWTs.
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Establece la clave secreta para firmar/verificar JWTs.
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Retorna el tiempo de expiración del token en segundos.
     */
    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    /**
     * Establece el tiempo de expiración del token en segundos.
     */
    public void setExpirationSeconds(long expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }
}
