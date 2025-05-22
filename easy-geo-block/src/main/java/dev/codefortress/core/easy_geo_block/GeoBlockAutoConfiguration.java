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
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties({GeoBlockProperties.class, SecuritySuiteLicenseProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.geo-block", name = "enabled", havingValue = "true", matchIfMissing = true)
public class GeoBlockAutoConfiguration implements WebMvcConfigurer {

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
        return new IpApiGeoLocationProvider(); // reemplazable por GeoLite2
    }

    @Bean
    public GeoBlockService geoBlockService(GeoLocationProvider provider, GeoBlockProperties props) {
        return new GeoBlockService(provider, props);
    }

    @Bean
    public GeoBlockInterceptor geoBlockInterceptor(
        GeoBlockService service,
        SecuritySuiteLicenseProperties props,
        LicenseValidator validator,
        LicenseEnvironmentResolver environmentResolver
    ) {
        LicenseCheckResult result = validator.validate(
            props.getProduct(),
            props.getKey(),
            environmentResolver.resolveDomain()
        );
        if (!result.isValid()) {
            throw new LicenseException("Geo-block está disponible solo con licencia activa.");
        }
        return new GeoBlockInterceptor(service);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        new DelegatingInterceptorRegistry()
            .addInterceptor(context.getBean(GeoBlockInterceptor.class), 1)
            .applyTo(registry);
    }
}
