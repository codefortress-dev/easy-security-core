package dev.codefortress.core.easy_licensing;

import java.util.function.Supplier;

/**
 * Fábrica que construye el validador con fallback automático.
 * Permite inyectar solo lo necesario desde una AutoConfiguration.
 */


public class LicenseValidatorFactory {

    private final Supplier<ModuleLicenseProperties> licenseProps;
    private final Supplier<LicenseEnvironmentResolver> environmentResolver;
    private final Supplier<TrialMetadataStore> trialStore;

    public LicenseValidatorFactory(
            Supplier<ModuleLicenseProperties> licenseProps,
            Supplier<LicenseEnvironmentResolver> environmentResolver,
            Supplier<TrialMetadataStore> trialStore) {
        this.licenseProps = licenseProps;
        this.environmentResolver = environmentResolver;
        this.trialStore = trialStore;
    }

    public TrialAwareLicenseValidator build() {
        return new TrialAwareLicenseValidator(
                licenseProps.get(),
                new LicenseRemoteValidator(),
                trialStore.get(),
                environmentResolver.get()
        );
    }
}

