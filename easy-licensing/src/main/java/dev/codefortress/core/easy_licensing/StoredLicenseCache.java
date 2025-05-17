package dev.codefortress.core.easy_licensing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class StoredLicenseCache {

    private static final String FILE_NAME = ".easy-licenses.properties";

    private final File storage;

    public StoredLicenseCache() {
        this.storage = Path.of(System.getProperty("user.home"), FILE_NAME).toFile();
    }

    public void save(LicenseInfo license) {
        try {
            Properties props = load();
            String prefix = license.getProduct() + ".";
            props.setProperty(prefix + "domain", license.getDomain());
            props.setProperty(prefix + "signature", license.getSignature());
            if (license.getFingerprint() != null) {
                props.setProperty(prefix + "fingerprint", license.getFingerprint());
            }
            try (FileWriter writer = new FileWriter(storage)) {
                props.store(writer, "Licencias activadas CodeFortress");
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar la licencia en cache local.", e);
        }
    }

    public LicenseInfo load(String product) {
        try {
            Properties props = load();
            String domain = props.getProperty(product + ".domain");
            String signature = props.getProperty(product + ".signature");
            String fingerprint = props.getProperty(product + ".fingerprint");

            if (domain == null || signature == null) {
                return null;
            }

            LicenseInfo license = new LicenseInfo(product, domain, signature);
            license.setFingerprint(fingerprint);
            return license;

        } catch (IOException e) {
            return null;
        }
    }

    private Properties load() throws IOException {
        Properties props = new Properties();
        if (storage.exists()) {
            try (FileReader reader = new FileReader(storage)) {
                props.load(reader);
            }
        }
        return props;
    }

    public void clear(String product) {
        try {
            Properties props = load();
            String prefix = product + ".";
            props.remove(prefix + "domain");
            props.remove(prefix + "signature");
            props.remove(prefix + "fingerprint");
            try (FileWriter writer = new FileWriter(storage)) {
                props.store(writer, "Licencias actualizadas");
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo limpiar la cache local para el producto: " + product, e);
        }
    }
}
