package dev.codefortress.core.easy_config_ui;

import dev.codefortress.core.easy_licensing.condition.EasyLicenseProCondition;
import dev.codefortress.core.easy_context.CoreFoundationMarker;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * Auto-configuration for the embedded configuration UI.
 * Only activated when a commercial module is present AND
 * the license is valid or in trial period.
 */
@AutoConfiguration
@ConditionalOnBean(CoreFoundationMarker.class) // Required: presence of any commercial module
@org.springframework.context.annotation.Conditional(EasyLicenseProCondition.class) // License required or Trial
public class ConfigUiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(EasyConfigStore.class)
    public EasyConfigStore easyConfigStore() {
        return new InMemoryConfigStore();
    }

    @Bean
    public EasyConfigController easyConfigController(EasyConfigStore store) {
        return new EasyConfigController(store);
    }
}
