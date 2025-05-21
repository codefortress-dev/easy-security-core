package dev.codefortress.core.easy_geo_block;

import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_licensing.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Autoconfiguración para el módulo de bloqueo geográfico.
 * Solo se activa si está habilitado en el archivo de configuración.
 */
@AutoConfiguration
@EnableConfigurationProperties(GeoBlockProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.geo-block", name = "enabled", havingValue = "true")
public class GeoBlockAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public GeoBlockProperties validatedGeoBlockProperties(GeoBlockProperties props) {
        EasyConfigScanner.preload(GeoBlockProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    }

    @Bean
    public GeoLocationProvider geoLocationProvider() {
        return new IpApiGeoLocationProvider(); // se puede reemplazar en producción
    }

    @Bean
    public GeoBlockService geoBlockService(GeoLocationProvider provider, GeoBlockProperties props) {
        return new GeoBlockService(provider, props);
    }

    @Bean
    public GeoBlockInterceptor geoBlockInterceptor(
        GeoBlockService service,
        SecuritySuiteLicenseProperties props,
        LicenseValidator validator
    ) {
        LicenseCheckResult result = validator.validate(props);
        if (result != LicenseCheckResult.VALID && result != LicenseCheckResult.TRIAL) {
            throw new LicenseException("Geo-block está disponible solo con licencia activa.");
        }
        return new GeoBlockInterceptor(service);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(geoBlockInterceptor(null, null, null)) // Esto se corregirá con inyección real
                .order(1);
    }
}
