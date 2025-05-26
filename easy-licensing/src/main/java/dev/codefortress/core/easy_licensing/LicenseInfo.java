package dev.codefortress.core.easy_licensing;

/**
 * Información de licencia devuelta por el servidor o usada localmente.
 */
public class LicenseInfo {

    private String product;
    private String domain;
    private String signedToken;
    private String fingerprint;

    
public LicenseInfo(String product, String domain, String signedToken) {
        this.product = product;
        this.domain = domain;
        this.signedToken = signedToken;
    }

public String getFingerprint() {
    return fingerprint;
}

public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
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

    public String getSignedToken() {
        return signedToken;
    }

    public void setSignedToken(String signedToken) {
        this.signedToken = signedToken;
    }
}
