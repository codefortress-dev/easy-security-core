package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto configuración para el sistema de licencias.
 * Este archivo registra los componentes clave para la validación
 * de licencias Pro, activación por dominio, modo trial y verificación
 * de integridad mediante firmas y huellas.
 */
@AutoConfiguration
@EnableConfigurationProperties(SecuritySuiteLicenseProperties.class)
public class LicensingAutoConfiguration {

    @Bean
    public SecuritySuiteLicenseProperties validatedSecuritySuiteLicenseProperties(SecuritySuiteLicenseProperties props) {
        EasyConfigScanner.preload(SecuritySuiteLicenseProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    }

    @Bean
    @ConditionalOnMissingBean
    public LicenseEnvironmentResolver licenseEnvironmentResolver() {
        return new LicenseEnvironmentResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public StoredLicenseCache storedLicenseCache() {
        return new StoredLicenseCache();
    }

    @Bean
    @ConditionalOnMissingBean
    public TrialMetadataStore trialMetadataStore() {
        return new TrialMetadataStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public LicenseSignatureVerifier licenseSignatureVerifier() {
        // En producción, este valor debe ser configurable vía YAML o entorno
        return new LicenseSignatureVerifier("LICENSE_SECRET");
    }

    @Bean
    @ConditionalOnMissingBean
    public LicenseRemoteValidator licenseRemoteValidator() {
        return new LicenseRemoteValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    public LicenseValidator licenseValidator(
        SecuritySuiteLicenseProperties props,
        LicenseEnvironmentResolver env,
        LicenseRemoteValidator remote,
        StoredLicenseCache cache,
        LicenseSignatureVerifier verifier
    ) {
        return new LicenseValidator(props, env, remote, cache, verifier);
    }

    @Bean
    @ConditionalOnMissingBean
    public TrialAwareLicenseValidator trialAwareLicenseValidator(
        LicenseValidator validator,
        TrialMetadataStore trialStore,
        SecuritySuiteLicenseProperties props,
        LicenseEnvironmentResolver env,
        StoredLicenseCache cache
    ) {
        return new TrialAwareLicenseValidator(validator, trialStore, props, env, cache);
    }

    @Bean
    @ConditionalOnMissingBean
    public LicenseFingerprintProvider licenseFingerprintProvider(
        SecuritySuiteLicenseProperties props,
        StoredLicenseCache cache
    ) {
        return new LicenseFingerprintProvider(props, cache);
    }
}
