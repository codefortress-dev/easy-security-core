package dev.codefortress.core.easy_licensing;

/**
 * Proveedor que expone la huella actual de la licencia válida del módulo en ejecución.
 * Puede ser utilizado para diagnósticos, auditoría, panel de administración o métricas.
 */
public class LicenseFingerprintProvider {

    private final ModuleLicenseProperties properties;
    private final StoredLicenseCache cache;

    public LicenseFingerprintProvider(ModuleLicenseProperties properties, StoredLicenseCache cache) {
        this.properties = properties;
        this.cache = cache;
    }

    /**
     * Retorna la huella actual del producto registrado (o null si no está cacheada).
     */
    public String getFingerprint() {
        LicenseInfo info = cache.load(properties.getProduct());
        return info != null ? info.getFingerprint() : null;
    }

    /**
     * Retorna el nombre del producto para el cual se consultará la huella.
     */
    public String getProduct() {
        return properties.getProduct();
    }
}
