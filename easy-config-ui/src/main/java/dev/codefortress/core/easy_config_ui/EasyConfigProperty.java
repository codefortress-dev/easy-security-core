package dev.codefortress.core.easy_config_ui;

import java.lang.annotation.*;

/**
 * Marca un campo como configurable en caliente.
 * Los valores anotados podrán ser expuestos y modificados en la UI
 * o API embebida de configuración, si la licencia lo permite.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EasyConfigProperty {

    /**
     * Nombre alternativo del campo.
     * Si se omite, se usará el nombre real de la propiedad.
     */
    String value() default "";

    /**
     * Descripción opcional para mostrar en la interfaz.
     */
    String description() default "";

    /**
     * Indica si el campo es obligatorio.
     */
    boolean required() default false;

    /**
     * Aplica solo a Strings: no se permiten valores vacíos o en blanco.
     */
    boolean nonBlank() default false;

    /**
     * Valor mínimo permitido (para numéricos).
     */
    long min() default Long.MIN_VALUE;

    /**
     * Valor máximo permitido (para numéricos).
     */
    long max() default Long.MAX_VALUE;
}
