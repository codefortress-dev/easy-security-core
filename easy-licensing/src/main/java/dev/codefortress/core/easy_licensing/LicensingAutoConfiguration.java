package dev.codefortress.core.easy_licensing;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import dev.codefortress.core.easy_config_ui.EasyConfigScanner;

@AutoConfiguration
@EnableConfigurationProperties(SecuritySuiteLicenseProperties.class)
public class LicensingAutoConfiguration {

    @Bean
    public SecuritySuiteLicenseProperties securitySuiteLicenseProperties() {
        EasyConfigScanner.preload(SecuritySuiteLicenseProperties.class);
        return new SecuritySuiteLicenseProperties();
    }

    @Bean
    public LicenseValidator licenseValidator(
        LicenseEnvironmentResolver env,
        LicenseRemoteValidator remote,
        StoredLicenseCache cache,
        LicenseSignatureVerifier verifier) {

        return new LicenseValidator(env, remote, cache, verifier);
    }

    // Otros beans como LicenseRemoteValidator, StoredLicenseCache, etc., se configuran igual
}

}