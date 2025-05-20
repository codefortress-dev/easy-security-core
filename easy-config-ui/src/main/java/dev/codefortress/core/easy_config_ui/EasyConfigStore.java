package dev.codefortress.core.easy_config_ui;

/**
 * Interfaz que define un almacén de configuración dinámica.
 * Implementaciones pueden usar memoria, base de datos, Redis, etc.
 */
public interface EasyConfigStore {

    /**
     * Obtiene el valor asociado a una clave.
     *
     * @param key clave de configuración
     * @return valor como String, o null si no existe
     */
    String get(String key);

    /**
     * Almacena o actualiza una clave con su valor.
     *
     * @param key clave
     * @param value valor como texto
     */
    void set(String key, String value);

    /**
     * Indica si el almacén contiene una clave específica.
     */
    boolean contains(String key);
}
