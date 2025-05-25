package dev.codefortress.core.easy_licensing;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validador central de licencias Pro.
 * Esta clase verifica si la licencia es válida para el producto,
 * si el dominio es correcto, si la firma es válida y si debe persistirse localmente.
 *
 * Es usada por todos los módulos comerciales de CodeFortress.
 */
public class LicenseValidator {
    private static final Logger log = LoggerFactory.getLogger(LicenseValidator.class);

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
            log.warn("Todos los parámetros (email, clave, proyecto o dominio) son obligatorios.");
            return LicenseCheckResult.INVALID;
        }

        String product = properties.getProduct();
        String key = properties.getKey();

        LicenseInfo licencia = remoteValidator.validate(product, key, currentDomain);

        if (licencia == null) {
            log.warn("No se encontró una licencia válida para el producto: {}", product);
            return LicenseCheckResult.INVALID;
        }

        if (licencia.getDomain() == null || licencia.getDomain().isBlank()) {
            licencia.setDomain(currentDomain);
            cache.save(licencia);
        } else if (!Objects.equals(licencia.getDomain(), currentDomain)) {
            log.warn("La licencia está registrada para otro dominio: {} ≠ {}", licencia.getDomain(), currentDomain);
            return LicenseCheckResult.INVALID;
        }

        if (!verifier.verify(licencia)) {
            log.warn("Firma de licencia no válida para el producto '{}'", product);
            return LicenseCheckResult.INVALID;
        }

        LicenseFingerprintGenerator generator = new LicenseFingerprintGenerator();
        licencia.setFingerprint(generator.generateFingerprint(licencia));
        cache.save(licencia);

        log.info("Licencia válida registrada para el producto '{}' en dominio '{}'", product, currentDomain);
        return LicenseCheckResult.VALID;

    }
}
