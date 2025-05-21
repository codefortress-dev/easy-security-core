package dev.codefortress.core.easy_config_persistence;

import dev.codefortress.core.easy_config_ui.EasyConfigStore;
import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass({EasyConfigStore.class, JpaConfigStore.class})
@EnableConfigurationProperties(ConfigPersistenceProperties.class)
public class ConfigPersistenceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EasyConfigStore easyConfigStore(ConfigPersistenceProperties properties, ConfigRepository repository) {
        ConfigurationValidator.validate(properties);

        if (!properties.isEnabled()) {
            return new EasyConfigStore() {
                @Override
                public String get(String key) {
                    return null;
                }

                @Override
                public void set(String key, String value) {
                    // no-op
                }

                @Override
                public boolean contains(String key) {
                    return false;
                }
            };
        }

        return new JpaConfigStore(repository);
    }

    @Bean
    public JpaConfigStoreSaver jpaConfigStoreSaver(EasyConfigStore store, ConfigRepository repository) {
        return new JpaConfigStoreSaver(store, repository);
    }

    @Bean
    public JpaConfigStoreInitializer jpaConfigStoreInitializer(JpaConfigStore jpaStore,
                                                               EasyConfigStore activeStore,
                                                               ConfigRepository repository) {
        return new JpaConfigStoreInitializer(jpaStore, activeStore, repository);
    }
}
