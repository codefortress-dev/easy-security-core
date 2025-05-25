package dev.codefortress.core.easy_licensing;

import java.net.InetAddress;

/**
 * Permite resolver el dominio actual y detectar si se está ejecutando en localhost.
 */
public class LicenseEnvironmentResolver {

    public String resolveDomain() {
        try {
            String host = InetAddress.getLocalHost().getHostName();
            if (isLocalhost(host)) {
                return "localhost";
            }
            return host;
        } catch (Exception e) {
            return "localhost"; // Fallback por seguridad
        }
    }

    public boolean isLocalhost(String host) {
        return host == null
                || host.equalsIgnoreCase("localhost")
                || host.equals("127.0.0.1")
                || host.startsWith("192.168.") // redes locales comunes
                || host.endsWith(".local")
                || host.contains("dev");
    }
}
