package dev.codefortress.core.easy_geo_block;

import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_context.common.DelegatingInterceptorRegistry;
import dev.codefortress.core.easy_licensing.*;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguración para el módulo de bloqueo geográfico.
 * Se activa solo si está habilitado en la configuración.
 */
@AutoConfiguration
@EnableConfigurationProperties({GeoBlockProperties.class, ModuleLicenseProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.geo-block", name = "enabled", havingValue = "true")
public class GeoBlockAutoConfiguration {

    private final ApplicationContext context;

    public GeoBlockAutoConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public GeoBlockProperties validatedGeoBlockProperties(GeoBlockProperties props) {
        EasyConfigScanner.preload(GeoBlockProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    }

    @Bean
    public GeoLocationProvider geoLocationProvider() {
        return new IpApiGeoLocationProvider(); // en futuro: GeoLite2
    }

    @Bean
    public GeoBlockService geoBlockService(GeoLocationProvider provider, GeoBlockProperties props) {
        return new GeoBlockService(provider, props);
    }

    @Bean
    public GeoBlockInterceptor geoBlockInterceptor(
        GeoBlockService service,
        ModuleLicenseProperties licenseProps,
        LicenseValidator validator,
        LicenseEnvironmentResolver resolver
    ) {
        LicenseCheckResult result = validator.validate(
            licenseProps.getProduct(),
            licenseProps.getKey(),
            resolver.resolveDomain()
        );
        if (!result.isValid() && !result.isTrial()) {
            throw new LicenseException("GeoBlock está disponible solo con licencia activa.");
        }

        return new GeoBlockInterceptor(service);
    }

    @Bean
    public Object geoBlockInterceptorRegistration(
        DelegatingInterceptorRegistry registry,
        GeoBlockInterceptor interceptor
    ) {
        registry.addInterceptor(interceptor,1);
        return new Object(); // marcador para forzar inyección
    }
}
