package dev.codefortress.core.easy_config_persistence;

import dev.codefortress.core.easy_config_ui.EasyConfigStore;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnClass(EasyConfigStore.class)
public class JpaConfigStoreInitializer {

    private static final Logger log = LoggerFactory.getLogger(JpaConfigStoreInitializer.class);

    private final EasyConfigStore activeStore;
    private final ConfigRepository repository;

    public JpaConfigStoreInitializer(
        EasyConfigStore activeStore,
        ConfigRepository repository
    ) {
        this.activeStore = activeStore;
        this.repository = repository;
    }

    @PostConstruct
    public void applyDatabaseConfigurations() {
        List<ConfigEntity> configs = repository.findAll();

        for (ConfigEntity entity : configs) {
            String key = entity.getKey();
            String value = entity.getValue();
            activeStore.set(key, value);
        }

        log.info("[easy-config-persistence] Configuración cargada desde base de datos ({} propiedades).", configs.size());
    }
}
