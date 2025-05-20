package dev.codefortress.core.easy_licensing;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Auto configuración exclusiva para exponer el proveedor
 * de huellas digitales activas, utilizado en métricas, trazas
 * o interfaces administrativas.
 *
 * Este bean se puede incluir sin cargar el sistema completo de licencias.
 */
@AutoConfiguration
public class LicenseFingerprintAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LicenseFingerprintProvider licenseFingerprintProvider(
            SecuritySuiteLicenseProperties properties,
            StoredLicenseCache cache) {
        return new LicenseFingerprintProvider(properties, cache);
    }
}
