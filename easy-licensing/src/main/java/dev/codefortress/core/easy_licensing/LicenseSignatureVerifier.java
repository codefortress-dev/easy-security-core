package dev.codefortress.core.easy_licensing;

import dev.codefortress.core.easy_config_ui.annotation.EasyConfigProperty;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

public class LicenseSignatureVerifier {

    @EasyConfigProperty("easy.license.signature-secret")
    private String secret = "default-signature-key";

    public LicenseSignatureVerifier() {}

    public boolean verify(LicenseInfo license) {
        try {
            String data = license.getProduct() + ":" + license.getDomain() + ":" + secret;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            String generated = Base64.getEncoder().encodeToString(hash);
            return Objects.equals(generated, license.getSignature());
        } catch (Exception e) {
            return false;
        }
    }

    public String generate(LicenseInfo license) {
        try {
            String data = license.getProduct() + ":" + license.getDomain() + ":" + secret;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            return null;
        }
    }
}
