package dev.codefortress.core.easy_licensing;

/**
 * Excepción lanzada cuando ocurre un error crítico relacionado con
 * la validación, activación o configuración de una licencia Pro.
 */
public class LicenseException extends RuntimeException {

    /**
     * Crea una nueva excepción de licencia con el mensaje proporcionado.
     *
     * @param message descripción del error
     */
    public LicenseException(String message) {
        super(message);
    }
}
