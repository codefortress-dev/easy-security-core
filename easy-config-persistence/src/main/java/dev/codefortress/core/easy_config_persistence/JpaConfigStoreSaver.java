package dev.codefortress.core.easy_config_persistence;

import dev.codefortress.core.easy_config_ui.EasyConfigStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConditionalOnClass(EasyConfigStore.class)
public class JpaConfigStoreSaver implements EasyConfigStore {

    private static final Logger log = LoggerFactory.getLogger(JpaConfigStoreSaver.class);

    private final EasyConfigStore delegate;
    private final ConfigRepository repository;

    public JpaConfigStoreSaver(EasyConfigStore delegate, ConfigRepository repository) {
        this.delegate = delegate;
        this.repository = repository;
    }

    @Override
    public String get(String key) {
        return delegate.get(key);
    }

    @Override
    public void set(String key, String value) {
        String current = delegate.get(key);

        delegate.set(key, value); // Actualiza en memoria

        if (!Objects.equals(current, value)) {
            ConfigEntity entity = repository.findById(key).orElse(new ConfigEntity());
            entity.setKey(key);
            entity.setValue(value);
            entity.setSource("UI");
            repository.save(entity);
            log.info("Propiedad '{}' actualizada a '{}' y persistida en base de datos.", key, value);
        }
    }

    @Override
    public boolean contains(String key) {
        return delegate.contains(key);
    }
}
