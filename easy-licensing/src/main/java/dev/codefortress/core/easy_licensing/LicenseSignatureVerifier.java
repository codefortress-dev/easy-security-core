package dev.codefortress.core.easy_licensing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

/**
 * Verifica o genera la firma digital de una licencia.
 * Utiliza una combinación del producto, dominio y un secreto de firma
 * para garantizar que las licencias no puedan ser falsificadas.
 *
 * El algoritmo usado es SHA-256 + Base64.
 */
public class LicenseSignatureVerifier {

    private final String secret;

    /**
     * Crea un verificador con el secreto proporcionado.
     *
     * @param secret clave de firma compartida entre cliente y servidor
     */
    public LicenseSignatureVerifier(String secret) {
        this.secret = secret;
    }

    /**
     * Verifica que la firma de la licencia sea válida.
     *
     * @param license la licencia a verificar
     * @return true si la firma es válida, false si no coincide
     */
    public boolean verify(LicenseInfo license) {
        try {
            String data = license.getProduct() + ":" + license.getDomain() + ":" + secret;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            String generated = Base64.getEncoder().encodeToString(hash);
            return Objects.equals(generated, license.getSignedToken());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Genera la firma esperada para una licencia dada.
     *
     * @param license licencia a firmar
     * @return firma en Base64
     */
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
