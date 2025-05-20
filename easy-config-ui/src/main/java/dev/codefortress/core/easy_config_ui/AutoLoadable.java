package dev.codefortress.core.easy_config_ui;

import java.lang.annotation.*;

/**
 * Anotación que marca una clase como auto-cargable
 * para la configuración dinámica.
 *
 * Todas las propiedades marcadas con @EasyConfigProperty
 * dentro de esta clase podrán ser descubiertas en caliente.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoLoadable {
}
