package dev.codefortress.core.easy_config_persistence;

import dev.codefortress.core.easy_config_ui.AutoLoadable;
import dev.codefortress.core.easy_config_ui.EasyConfigProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para la persistencia de configuraciones dinámicas.
 * Este módulo permite almacenar en base de datos las propiedades modificadas en caliente
 * desde la interfaz visual provista por easy-config-ui.
 */
@ConfigurationProperties(prefix = "easy.config.persistence")
@AutoLoadable
public class ConfigPersistenceProperties {

    @EasyConfigProperty(description = "Habilita la persistencia de configuraciones dinámicas en base de datos")
    private boolean enabled = true;

    @EasyConfigProperty(description = "Nombre de la tabla que almacena las propiedades configurables")
    private String tableName = "easy_config_properties";

    @EasyConfigProperty(description = "Indica si se debe crear automáticamente la tabla si no existe")
    private boolean createTableIfMissing = false;

    // Getters y Setters
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isCreateTableIfMissing() {
        return createTableIfMissing;
    }

    public void setCreateTableIfMissing(boolean createTableIfMissing) {
        this.createTableIfMissing = createTableIfMissing;
    }
}
