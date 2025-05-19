package dev.codefortress.core.easy_security_core_jwt;

import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguración para el submódulo easy-security-core-jwt.
 * Registra automáticamente las propiedades y el utilitario de JWT,
 * habilitando su uso desde cualquier módulo comercial sin configuración adicional.
 */
@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {

    /**
     * Registra y valida anticipadamente las propiedades de JWT.
     * También permite que sean modificadas en caliente si se integra con easy-config-ui.
     */
    @Bean
    public JwtProperties validatedJwtProperties(JwtProperties props) {
        EasyConfigScanner.preload(JwtProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    }

    /**
     * Crea el utilitario de JWT listo para usarse con las propiedades configuradas.
     */
    @Bean
    public JwtTokenUtil jwtTokenUtil(JwtProperties props) {
        return new JwtTokenUtil(props.getSecretKey(), props.getExpirationSeconds());
    }
}
