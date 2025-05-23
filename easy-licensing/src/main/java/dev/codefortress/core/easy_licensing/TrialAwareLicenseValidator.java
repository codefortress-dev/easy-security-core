package dev.codefortress.core.easy_licensing;

/**
 * Validador extendido que permite validar una licencia normalmente
 * y, en caso de falla, intenta activar el modo de prueba (trial)
 * si está dentro del período permitido.
 */
public class TrialAwareLicenseValidator {

    private final LicenseValidator licenseValidator;
    private final TrialMetadataStore trialStore;
    private final ModuleLicenseProperties properties;
    private final LicenseEnvironmentResolver envResolver;
    private final StoredLicenseCache cache;

    public TrialAwareLicenseValidator(
            LicenseValidator licenseValidator,
            TrialMetadataStore trialStore,
            ModuleLicenseProperties properties,
            LicenseEnvironmentResolver envResolver,
            StoredLicenseCache cache
    ) {
        this.licenseValidator = licenseValidator;
        this.trialStore = trialStore;
        this.properties = properties;
        this.envResolver = envResolver;
        this.cache = cache;
    }

    /**
     * Valida la licencia del módulo. Si no es válida, activa el modo trial.
     *
     * @param email correo del usuario
     * @param password clave del usuario
     * @param projectOrDomain nombre del proyecto (si no tiene dominio) o dominio registrado
     * @return resultado de la validación (válida, trial o inválida)
     */
    public LicenseCheckResult validate(String email, String password, String projectOrDomain) {
        LicenseCheckResult result = licenseValidator.validate(email, password, projectOrDomain);

        if (result.isValid()) {
            return result;
        }

        // Si no es válida, intentamos modo trial
        trialStore.ensureTrialStarted();
        if (trialStore.isTrialValid()) {
            LicenseInfo dummy = new LicenseInfo(
                    properties.getProduct(),
                    envResolver.resolveDomain(),
                    "trial-mode"
            );
            LicenseFingerprintGenerator generator = new LicenseFingerprintGenerator();
            dummy.setFingerprint(generator.generateFingerprint(dummy));
            cache.save(dummy);
            return LicenseCheckResult.trial("Modo prueba activo. Quedan días disponibles.");
        }

        return result; // Sigue siendo inválido
    }
}
