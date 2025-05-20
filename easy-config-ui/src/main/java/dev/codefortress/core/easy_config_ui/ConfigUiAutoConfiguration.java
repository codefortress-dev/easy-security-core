package dev.codefortress.core.easy_config_ui;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * Auto configuración para el sistema de configuración dinámica.
 * Expone el controlador embebido /easy-config y registra
 * un almacén en memoria por defecto si no se proporciona otro.
 */
@AutoConfiguration
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
