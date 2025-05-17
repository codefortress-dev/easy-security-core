package dev.codefortress.core.easy_licensing;

public class LicenseInfo {

    private String product;
    private String domain;
    private String signature;
    private String fingerprint; // Campo nuevo

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
