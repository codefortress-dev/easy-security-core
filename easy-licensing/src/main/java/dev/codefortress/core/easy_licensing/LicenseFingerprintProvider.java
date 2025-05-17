package dev.codefortress.core.easy_licensing;

public class LicenseFingerprintProvider {

    private final SecuritySuiteLicenseProperties properties;
    private final StoredLicenseCache cache;

    public LicenseFingerprintProvider(SecuritySuiteLicenseProperties properties, StoredLicenseCache cache) {
        this.properties = properties;
        this.cache = cache;
    }

    public String getActiveFingerprint() {
        LicenseInfo license = cache.load(properties.getProduct());
        if (license != null && license.getFingerprint() != null) {
            return license.getFingerprint();
        }
        return "unknown-fingerprint";
    }
}
