package dev.codefortress.core.easy_licensing;

/**
 * Fábrica reutilizable de instancias de LicenseValidator
 * para distintos módulos Pro.
 *
 * Permite evitar duplicación de dependencias comunes
 * y facilita la extensión hacia cualquier producto.
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

    /**
     * Crea una instancia de LicenseValidator usando un conjunto de propiedades de licencia genéricas.
     */
    public LicenseValidator create(ModuleLicenseProperties props) {
        return new LicenseValidator(props, envResolver, remoteValidator, cache, verifier);
    }
}
