package dev.codefortress.core.easy_licensing;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

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
