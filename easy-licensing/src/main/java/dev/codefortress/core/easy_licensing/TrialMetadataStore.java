package dev.codefortress.core.easy_licensing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Properties;

public class TrialMetadataStore {

    private static final String FILE_NAME = ".easy-trial.properties";
    private static final String KEY_START_DATE = "trial.start";
    private static final int TRIAL_DAYS = 21;

    private final File storage;

    public TrialMetadataStore() {
        this.storage = Path.of(System.getProperty("user.home"), FILE_NAME).toFile();
    }

    public boolean isTrialValid() {
        LocalDate start = getTrialStartDate();
        return start != null && !start.plusDays(TRIAL_DAYS).isBefore(LocalDate.now());
    }

    public boolean isTrialExpired() {
        return !isTrialValid();
    }

    public LocalDate getTrialStartDate() {
        try {
            if (!storage.exists()) return null;

            Properties props = new Properties();
            try (FileReader reader = new FileReader(storage)) {
                props.load(reader);
            }
            String raw = props.getProperty(KEY_START_DATE);
            return raw != null ? LocalDate.parse(raw) : null;

        } catch (IOException e) {
            return null;
        }
    }

    public void ensureTrialStarted() {
        if (getTrialStartDate() != null) return;

        try {
            Properties props = new Properties();
            props.setProperty(KEY_START_DATE, LocalDate.now().toString());
            try (FileWriter writer = new FileWriter(storage)) {
                props.store(writer, "Trial start metadata");
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo registrar el inicio del periodo de prueba.", e);
        }
    }
}
