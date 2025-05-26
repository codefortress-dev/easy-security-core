package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto configuración del sistema de licencias.
 * Registra el validador y todas las dependencias necesarias.
 */
@AutoConfiguration
@EnableConfigurationProperties({ModuleLicenseProperties.class, LicenseSignatureProperties.class})
public class LicensingAutoConfiguration {

   /*  @Bean
    public ModuleLicenseProperties validatedProps(ModuleLicenseProperties props) {
        EasyConfigScanner.preload(ModuleLicenseProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    } */

    @Bean
    public LicenseEnvironmentResolver environmentResolver() {
        return new LicenseEnvironmentResolver();
    }

    @Bean
    public TrialMetadataStore trialMetadataStore() {
        return new TrialMetadataStore();
    }

    @Bean
    public LicenseValidatorFactory licenseValidatorFactory(
            ModuleLicenseProperties props,
            LicenseEnvironmentResolver resolver,
            TrialMetadataStore store
    ) {
        return new LicenseValidatorFactory(() -> props, () -> resolver, () -> store);
    }

    @Bean
    public TrialAwareLicenseValidator licenseValidator(LicenseValidatorFactory factory) {
        return factory.build();
    }
}
