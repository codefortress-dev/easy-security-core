package dev.codefortress.core.easy_config_ui;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Escanea clases anotadas con @AutoLoadable para registrar
 * propiedades dinámicas marcadas con @EasyConfigProperty.
 *
 * Esta clase mantiene un registro de todas las configuraciones
 * disponibles en caliente para ser mostradas en la UI/API.
 */
public class EasyConfigScanner {

    private static final Map<String, ConfigMetadata> properties = new ConcurrentHashMap<>();

    /**
     * Escanea una clase y registra todas las propiedades configurables.
     *
     * @param clazz clase a escanear
     */
    public static void preload(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(AutoLoadable.class)) return;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(EasyConfigProperty.class)) {
                field.setAccessible(true);
                EasyConfigProperty annotation = field.getAnnotation(EasyConfigProperty.class);
                properties.put(field.getName(), new ConfigMetadata(field.getName(), annotation.description(), clazz));
            }
        }
    }

    /**
     * Retorna todos los metadatos escaneados.
     */
    public static Collection<ConfigMetadata> getAll() {
        return properties.values();
    }

    /**
     * Retorna los metadatos de una propiedad por clave.
     */
    public static ConfigMetadata get(String key) {
        return properties.get(key);
    }

    /**
     * Representa los metadatos asociados a una propiedad configurable.
     */
    public record ConfigMetadata(String key, String description, Class<?> source) {}
}
