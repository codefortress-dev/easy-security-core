package dev.codefortress.core.easy_licensing;

/**
 * Representa una licencia activa, ya sea recuperada del servidor
 * o almacenada localmente en caché.
 */
public class LicenseInfo {

    private String product;
    private String domain;
    private String signature;
    private String fingerprint;

    public LicenseInfo() {}

    public LicenseInfo(String product, String domain, String signature) {
        this.product = product;
        this.domain = domain;
        this.signature = signature;
    }

    /**
     * Nombre del producto asociado a la licencia.
     * Ej: "security-suite", "gateway-suite"
     */
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Dominio para el cual fue activada la licencia.
     */
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Firma digital que valida la integridad de la licencia.
     */
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Huella generada localmente para validación offline.
     */
    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public String toString() {
        return "LicenseInfo{" +
                "product='" + product + '\'' +
                ", domain='" + domain + '\'' +
                ", signature='" + signature + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                '}';
    }
}
