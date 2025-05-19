package dev.codefortress.core.easy_licensing;

/**
 * Fábrica de validadores reutilizable para múltiples módulos.
 */
public class LicenseValidatorFactory {

    private final LicenseEnvironmentResolver envResolver;
    private final LicenseRemoteValidator remoteValidator;
    private final StoredLicenseCache cache;
    private final LicenseSignatureVerifier verifier;

    public LicenseValidatorFactory(
            LicenseEnvironmentResolver envResolver,
            LicenseRemoteValidator remoteValidator,
            StoredLicenseCache cache,
            LicenseSignatureVerifier verifier) {
        this.envResolver = envResolver;
        this.remoteValidator = remoteValidator;
        this.cache = cache;
        this.verifier = verifier;
    }

    public LicenseValidator create(SecuritySuiteLicenseProperties props) {
        return new LicenseValidator(props, envResolver, remoteValidator, cache, verifier);
    }

    // En el futuro: agregar otros tipos de propiedades
    // public LicenseValidator create(GatewaySuiteLicenseProperties props) { ... }
}
