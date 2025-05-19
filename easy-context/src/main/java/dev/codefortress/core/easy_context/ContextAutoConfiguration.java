package dev.codefortress.core.easy_context;

import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * Autoconfiguración para el submódulo easy-context.
 *
 * Esta clase permite que Spring Boot detecte automáticamente el paquete
 * y active el contexto de ejecución sin configuración manual.
 *
 * Aunque no se registran beans por defecto en este momento,
 * esta clase es útil para futuras expansiones (ej: filtros de auditoría).
 */
@AutoConfiguration
public class ContextAutoConfiguration {
    // No se registran beans por defecto; reservado para futuras integraciones
}
