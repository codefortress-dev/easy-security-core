package dev.codefortress.core.easy_licensing;

/**
 * Representa una licencia activa, ya sea recuperada del servidor
 * o almacenada localmente en caché.
 */
public class LicenseInfo {

    /**
     * Nombre del producto asociado a la licencia (ej: "security-suite", "gateway-suite").
     */
    private String product;

    /**
     * Dominio registrado para esta licencia.
     */
    private String domain;

    /**
     * Firma digital que valida la integridad y legitimidad de esta licencia.
     */
    private String signature;

    /**
     * Huella generada localmente para validar esta licencia en entornos offline.
     */
    private String fingerprint;

    public LicenseInfo() {}

    public LicenseInfo(String product, String domain, String signature) {
        this.product = product;
        this.domain = domain;
        this.signature = signature;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

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
