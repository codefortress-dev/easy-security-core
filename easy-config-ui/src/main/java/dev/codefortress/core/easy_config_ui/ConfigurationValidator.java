package dev.codefortress.core.easy_config_ui;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Valida las propiedades marcadas con @EasyConfigProperty
 * dentro de clases anotadas con @AutoLoadable.
 *
 * Esta clase se invoca al inicio de la aplicación para asegurar
 * que las configuraciones mínimas estén presentes y sean válidas.
 */
public class ConfigurationValidator {

    /**
     * Valida una instancia de configuración completa.
     *
     * @param configInstance clase con anotaciones de propiedades
     */
    public static void validate(Object configInstance) {
        Class<?> clazz = configInstance.getClass();

        if (!clazz.isAnnotationPresent(AutoLoadable.class)) return;

        List<String> errores = new java.util.ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            EasyConfigProperty meta = field.getAnnotation(EasyConfigProperty.class);
            if (meta == null) continue;

            try {
                Object value = field.get(configInstance);
                String key = field.getName();

                if (meta.required() && value == null) {
                    errores.add("Propiedad '" + key + "' es requerida pero está vacía.");
                }

                if (value instanceof String str) {
                    if (meta.nonBlank() && str.isBlank()) {
                        errores.add("Propiedad '" + key + "' no debe estar en blanco.");
                    }
                }

                if (value instanceof Number num) {
                    long val = num.longValue();
                    if (val < meta.min()) {
                        errores.add("Propiedad '" + key + "' no puede ser menor a " + meta.min());
                    }
                    if (val > meta.max()) {
                        errores.add("Propiedad '" + key + "' no puede ser mayor a " + meta.max());
                    }
                }

            } catch (Exception e) {
                errores.add("Error validando '" + field.getName() + "': " + e.getMessage());
            }
        }

        if (!errores.isEmpty()) {
            System.err.println("Configuración inválida en clase: " + clazz.getSimpleName());
            errores.forEach(e -> System.err.println(" - " + e));
            throw new IllegalStateException("Error en configuración: " + clazz.getSimpleName());
        }
    }
}
