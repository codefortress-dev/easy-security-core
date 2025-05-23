package dev.codefortress.core.easy_licensing;

/**
 * Resultado de la validación de una licencia.
 * Indica si la licencia es válida, inválida o si está en modo de prueba (trial),
 * e incluye un mensaje descriptivo y, si aplica, los datos completos de la licencia.
 */
public class LicenseCheckResult {

    public enum Status {
        VALID, INVALID, TRIAL
    }

    private final Status status;
    private final LicenseInfo license;
    private final String message;

    private LicenseCheckResult(Status status, LicenseInfo license, String message) {
        this.status = status;
        this.license = license;
        this.message = message;
    }

    /** Crea un resultado de licencia válida. */
    public static LicenseCheckResult valid(LicenseInfo license) {
        return new LicenseCheckResult(Status.VALID, license, "Licencia válida.");
    }

    /** Crea un resultado de licencia inválida con un mensaje. */
    public static LicenseCheckResult invalid(String message) {
        return new LicenseCheckResult(Status.INVALID, null, message);
    }

    /** Crea un resultado en modo de prueba (trial) con un mensaje. */
    public static LicenseCheckResult trial(String message) {
        return new LicenseCheckResult(Status.TRIAL, null, message);
    }

    public boolean isValid() {
        return status == Status.VALID;
    }

    public boolean isTrial() {
        return status == Status.TRIAL;
    }

    public boolean isInvalid() {
        return status == Status.INVALID;
    }

    public Status getStatus() {
        return status;
    }

    public LicenseInfo getLicense() {
        return license;
    }

    public String getMessage() {
        return message;
    }
}
