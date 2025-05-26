package dev.codefortress.core.easy_licensing;

import java.util.Objects;

/**
 * Validador principal que decide automáticamente si se debe usar validación remota, local o fallback.
 */
public class TrialAwareLicenseValidator {

    private final ModuleLicenseProperties props;
    private final LicenseRemoteValidator remoteValidator;
    private final TrialMetadataStore trialMetadataStore;
    private final LicenseEnvironmentResolver environmentResolver;

    public TrialAwareLicenseValidator(
            ModuleLicenseProperties props,
            LicenseRemoteValidator remoteValidator,
            TrialMetadataStore trialMetadataStore,
            LicenseEnvironmentResolver environmentResolver) {
        this.props = props;
        this.remoteValidator = remoteValidator;
        this.trialMetadataStore = trialMetadataStore;
        this.environmentResolver = environmentResolver;
    }

    public LicenseCheckResult validate() {
        if (!props.isEnabled()) {
            return LicenseCheckResult.DISABLED;
        }

        String domain = environmentResolver.resolveDomain();

        // Si estamos en localhost o el backend está desactivado, no intentamos conexión remota
        if (environmentResolver.isLocalhost(domain) || !props.isRemoteValidation()) {
            return trialMetadataStore.getTrialStatus();
        }

        LicenseInfo remoteInfo = remoteValidator.validate(props.getProduct(), props.getKey(), domain);

        if (remoteInfo == null || !Objects.equals(remoteInfo.getProduct(), props.getProduct())) {
            return trialMetadataStore.getTrialStatus(); // fallback
        }

        trialMetadataStore.storeValidLicense(remoteInfo);
        return LicenseCheckResult.VALID;
    }
}
