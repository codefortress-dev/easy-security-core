package dev.codefortress.core.easy_licensing;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto configuración para exponer el proveedor de huellas de licencia activas,
 * usado para trazabilidad, métricas o administración.
 *
 * No requiere el sistema completo de validación para funcionar.
 */
@AutoConfiguration
@EnableConfigurationProperties(ModuleLicenseProperties.class)
public class LicenseFingerprintAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LicenseFingerprintProvider licenseFingerprintProvider(
            ModuleLicenseProperties properties,
            StoredLicenseCache cache
    ) {
        return new LicenseFingerprintProvider(properties, cache);
    }
}
