package dev.codefortress.core.easy_licensing;

/**
 * Proveedor de la huella digital (fingerprint) activa de la licencia,
 * útil para fines de auditoría, métricas o validación cruzada.
 */
public class LicenseFingerprintProvider {

    private final SecuritySuiteLicenseProperties properties;
    private final StoredLicenseCache cache;

    public LicenseFingerprintProvider(SecuritySuiteLicenseProperties properties, StoredLicenseCache cache) {
        this.properties = properties;
        this.cache = cache;
    }

    /**
     * Retorna la huella activa si existe en cache,
     * o una cadena por defecto si no se encuentra.
     */
    public String getActiveFingerprint() {
        LicenseInfo license = cache.load(properties.getProduct());
        if (license != null && license.getFingerprint() != null) {
            return license.getFingerprint();
        }
        return "unknown-fingerprint";
    }
}
