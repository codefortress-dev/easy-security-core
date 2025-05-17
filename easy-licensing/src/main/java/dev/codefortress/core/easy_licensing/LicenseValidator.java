package dev.codefortress.core.easy_licensing;

import java.util.List;
import java.util.Objects;

public class LicenseValidator {

    private final SecuritySuiteLicenseProperties properties;
    private final LicenseEnvironmentResolver envResolver;
    private final LicenseRemoteValidator remoteValidator;
    private final StoredLicenseCache cache;
    private final LicenseSignatureVerifier verifier;

    public LicenseValidator(SecuritySuiteLicenseProperties properties,
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

    public LicenseCheckResult validate(String email, String password, String projectOrDomain) {
        String currentDomain = envResolver.resolveDomain();

        if (email == null || email.isBlank() ||
            password == null || password.isBlank() ||
            projectOrDomain == null || projectOrDomain.isBlank()) {
            return LicenseCheckResult.invalid("Todos los parámetros (email, clave, proyecto o dominio) son obligatorios.");
        }

        List<LicenseInfo> licencias = remoteValidator.getLicenses(email, password);
        if (licencias.isEmpty()) {
            return LicenseCheckResult.invalid("No se encontraron licencias para este usuario.");
        }

        String product = properties.getProduct();
        LicenseInfo licencia = licencias.stream()
            .filter(l -> Objects.equals(l.getProduct(), product))
            .findFirst()
            .orElse(null);

        if (licencia == null) {
            return LicenseCheckResult.invalid("No se encontró una licencia válida para el producto: " + product);
        }

        // Validar y/o registrar dominio
        if (licencia.getDomain() == null || licencia.getDomain().isBlank()) {
            // Primera activación: reemplazamos el nombre del proyecto por el dominio actual
            licencia.setDomain(currentDomain);
            cache.save(licencia);
        } else if (!Objects.equals(licencia.getDomain(), currentDomain)) {
            return LicenseCheckResult.invalid("La licencia está registrada para otro dominio.");
        }

        // Validación de firma
        if (!verifier.verify(licencia)) {
            return LicenseCheckResult.invalid("Firma de licencia no válida.");
        }
        // Generar fingerprint y guardar
        LicenseFingerprintGenerator generator = new LicenseFingerprintGenerator();  
        licencia.setFingerprint(generator.generateFingerprint(licencia));
        cache.save(licencia);

        // Guardar en cache local
        cache.save(licencia);
        return LicenseCheckResult.valid(licencia);
    }
}
