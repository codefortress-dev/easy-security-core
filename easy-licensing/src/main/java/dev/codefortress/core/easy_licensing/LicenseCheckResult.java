package dev.codefortress.core.easy_licensing;

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

    public static LicenseCheckResult valid(LicenseInfo license) {
        return new LicenseCheckResult(Status.VALID, license, "Licencia válida.");
    }

    public static LicenseCheckResult invalid(String message) {
        return new LicenseCheckResult(Status.INVALID, null, message);
    }

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
