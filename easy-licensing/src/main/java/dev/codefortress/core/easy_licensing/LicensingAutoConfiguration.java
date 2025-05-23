package dev.codefortress.core.easy_licensing;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto configuración base del sistema de licencias Pro.
 * Registra todos los componentes esenciales para validar licencias, manejar caché,
 * validar firmas y generar huellas.
 */
@AutoConfiguration
@EnableConfigurationProperties(ModuleLicenseProperties.class)
public class LicensingAutoConfiguration {

    @Bean
    public LicenseEnvironmentResolver licenseEnvironmentResolver() {
        return new LicenseEnvironmentResolver();
    }

    @Bean
    public LicenseRemoteValidator licenseRemoteValidator() {
        return new LicenseRemoteValidator();
    }

    @Bean
    public StoredLicenseCache storedLicenseCache() {
        return new StoredLicenseCache();
    }

    @Bean
    public LicenseSignatureVerifier licenseSignatureVerifier(LicenseSignatureProperties props) {
        return new LicenseSignatureVerifier(props.getSecret());
    }

    @Bean
    public LicenseValidatorFactory licenseValidatorFactory(
        LicenseEnvironmentResolver resolver,
        LicenseRemoteValidator remoteValidator,
        StoredLicenseCache cache,
        LicenseSignatureVerifier verifier
    ) {
        return new LicenseValidatorFactory(resolver, remoteValidator, cache, verifier);
    }

    @Bean
    public LicenseValidator licenseValidator(
        LicenseValidatorFactory factory,
        ModuleLicenseProperties props
    ) {
        return factory.create(props);
    }

    @Bean
    public TrialMetadataStore trialMetadataStore() {
        return new TrialMetadataStore();
    }

    @Bean
    public TrialAwareLicenseValidator trialAwareLicenseValidator(
        LicenseValidator licenseValidator,
        TrialMetadataStore trialStore,
        ModuleLicenseProperties properties,
        LicenseEnvironmentResolver resolver,
        StoredLicenseCache cache
    ) {
        return new TrialAwareLicenseValidator(licenseValidator, trialStore, properties, resolver, cache);
    }
}
