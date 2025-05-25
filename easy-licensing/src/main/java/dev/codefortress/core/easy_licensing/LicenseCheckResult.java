package dev.codefortress.core.easy_licensing;

/**
 * Resultado estándar de la validación de una licencia.
 * Es usado por todos los validadores para representar el estado actual.
 */
public enum LicenseCheckResult {

    VALID("Licencia válida y activa"),
    TRIAL("Modo de prueba activo"),
    INVALID("Licencia inválida o no registrada"),
    DISABLED("Licenciamiento desactivado manualmente");

    private final String message;

    LicenseCheckResult(String message) {
        this.message = message;
    }

    public String getStatus() {
        return name();
    }

    public String getMessage() {
        return message;
    }

    public boolean isValid() {
        return this == VALID || this == TRIAL;
    }

    public boolean isTrial() {
        return this == TRIAL;
    }
}
