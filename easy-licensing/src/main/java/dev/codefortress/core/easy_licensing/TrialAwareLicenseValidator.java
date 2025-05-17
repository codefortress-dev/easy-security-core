package dev.codefortress.core.easy_licensing;

public class TrialAwareLicenseValidator {

    private final LicenseValidator licenseValidator;
    private final TrialMetadataStore trialStore;

    public TrialAwareLicenseValidator(LicenseValidator licenseValidator, TrialMetadataStore trialStore) {
        this.licenseValidator = licenseValidator;
        this.trialStore = trialStore;
    }

    public LicenseCheckResult validate(String email, String password, String projectOrDomain) {
        LicenseCheckResult result = licenseValidator.validate(email, password, projectOrDomain);
        if (result.isValid()) {
            return result;
        }

        // Si no es válido, intentamos modo trial
        trialStore.ensureTrialStarted();
       if (trialStore.isTrialValid()) {
        LicenseInfo dummy = new LicenseInfo(properties.getProduct(), envResolver.resolveDomain(), "trial-mode");
        LicenseFingerprintGenerator generator = new LicenseFingerprintGenerator();
        dummy.setFingerprint(generator.generateFingerprint(dummy));
        cache.save(dummy);
        return LicenseCheckResult.trial("Modo prueba activo. Quedan días disponibles.");
        }

        return result; // Sigue siendo inválido
    }
}
