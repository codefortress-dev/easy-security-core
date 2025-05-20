package dev.codefortress.core.easy_licensing;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Genera una huella digital (fingerprint) para la licencia actual,
 * con el fin de verificar que se mantenga en el entorno autorizado.
 *
 * La huella está compuesta por:
 * - Producto
 * - Dominio activado
 * - Nombre del archivo .jar que ejecuta la aplicación
 *
 * El algoritmo utilizado es SHA-256 y codificación Base64.
 */
public class LicenseFingerprintGenerator {

    /**
     * Genera la huella para la licencia proporcionada.
     *
     * @param license licencia validada
     * @return cadena Base64 que representa la huella
     */
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

    /**
     * Intenta obtener el nombre del archivo .jar que ejecuta la aplicación.
     */
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
