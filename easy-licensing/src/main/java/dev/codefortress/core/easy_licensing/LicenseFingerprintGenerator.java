package dev.codefortress.core.easy_licensing;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class LicenseFingerprintGenerator {

    public String generateFingerprint(LicenseInfo license) {
        try {
            String jarName = getJarFileName();
            String raw = license.getProduct() + "|" + license.getDomain() + "|" + jarName;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            return "unknown-fingerprint";
        }
    }

    private String getJarFileName() {
        try {
            String path = LicenseFingerprintGenerator.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
            File jarFile = new File(path);
            return jarFile.getName();
        } catch (Exception e) {
            return "unknown-jar";
        }
    }
}
