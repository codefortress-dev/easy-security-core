package dev.codefortress.core.easy_licensing;

import java.util.Objects;

/**
 * Validador central de licencias Pro.
 * Esta clase verifica si la licencia es válida para el producto,
 * si el dominio es correcto, si la firma es válida y si debe persistirse localmente.
 *
 * Es usada por todos los módulos comerciales de CodeFortress.
 */
public class LicenseValidator {

    private final ModuleLicenseProperties properties;
    private final LicenseEnvironmentResolver envResolver;
    private final LicenseRemoteValidator remoteValidator;
    private final StoredLicenseCache cache;
    private final LicenseSignatureVerifier verifier;

    public LicenseValidator(ModuleLicenseProperties properties,
                            LicenseEnvironmentResolver envResolver,
                            LicenseRemoteValidator remoteValidator,
                            StoredLicenseCache cache,
                            LicenseSignatureVerifier verifier) {
        this.properties = properties;
        this.envResolver = envResolver;
        this.remoteValidator = remoteValidator;
        this.cache = cache;
        this.verifier = verifier;
    }

    /**
     * Valida la licencia del producto actual para el entorno en ejecución.
     *
     * @param email correo del usuario que compró la licencia
     * @param password clave secreta asociada
     * @param projectOrDomain nombre del proyecto (si aún no tiene dominio) o dominio usado en la compra
     * @return resultado de la validación (válida, inválida o con error)
     */
    public LicenseCheckResult validate(String email, String password, String projectOrDomain) {
        String currentDomain = envResolver.resolveDomain();

        if (email == null || email.isBlank() ||
            password == null || password.isBlank() ||
            projectOrDomain == null || projectOrDomain.isBlank()) {
            return LicenseCheckResult.invalid("Todos los parámetros (email, clave, proyecto o dominio) son obligatorios.");
        }

        String product = properties.getProduct();
        String key = properties.getKey();

        LicenseInfo licencia = remoteValidator.validate(product, key, currentDomain);

        if (licencia == null) {
            return LicenseCheckResult.invalid("No se encontró una licencia válida para el producto: " + product);
        }

        if (licencia.getDomain() == null || licencia.getDomain().isBlank()) {
            licencia.setDomain(currentDomain);
            cache.save(licencia);
        } else if (!Objects.equals(licencia.getDomain(), currentDomain)) {
            return LicenseCheckResult.invalid("La licencia está registrada para otro dominio.");
        }

        if (!verifier.verify(licencia)) {
            return LicenseCheckResult.invalid("Firma de licencia no válida.");
        }

        LicenseFingerprintGenerator generator = new LicenseFingerprintGenerator();
        licencia.setFingerprint(generator.generateFingerprint(licencia));
        cache.save(licencia);

        return LicenseCheckResult.valid(licencia);
    }
}
