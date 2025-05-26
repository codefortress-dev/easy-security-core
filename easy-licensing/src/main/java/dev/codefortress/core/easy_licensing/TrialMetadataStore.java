package dev.codefortress.core.easy_licensing;

import java.time.LocalDateTime;
import java.util.prefs.Preferences;

/**
 * Permite gestionar el estado del modo de prueba (trial) y persistir su inicio.
 */
public class TrialMetadataStore {

    private static final String PREF_NODE = "dev.codefortress.license.trial";
    private static final String START_KEY = "trialStartDate";
    private static final int TRIAL_DAYS = 21;

    private final Preferences prefs;

    public TrialMetadataStore() {
        this.prefs = Preferences.userRoot().node(PREF_NODE);
    }

    public LicenseCheckResult getTrialStatus() {
        LocalDateTime start = getOrInitStart();
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(start.plusDays(TRIAL_DAYS))) {
            return LicenseCheckResult.TRIAL;
        } else {
            return LicenseCheckResult.INVALID;
        }
    }

    public void storeValidLicense(LicenseInfo info) {
        // Si en el futuro quieres guardar la fecha de activación, etc.
        // Por ahora, no hace nada
    }

    private LocalDateTime getOrInitStart() {
        String stored = prefs.get(START_KEY, null);
        if (stored != null) {
            return LocalDateTime.parse(stored);
        }

        LocalDateTime now = LocalDateTime.now();
        prefs.put(START_KEY, now.toString());
        return now;
    }
}
