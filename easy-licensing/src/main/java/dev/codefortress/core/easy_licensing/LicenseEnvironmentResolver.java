package dev.codefortress.core.easy_licensing;

/**
 * Resuelve el entorno de ejecución actual (local, dev o producción)
 * y el dominio donde se está ejecutando la aplicación.
 * 
 * Esta clase es utilizada para validar si una licencia debe activarse
 * o si puede funcionar en modo trial.
 */
public class LicenseEnvironmentResolver {

    /**
     * Determina si la aplicación se ejecuta en entorno de desarrollo o localhost.
     * 
     * Esto se usa para evitar exigir licencia en entornos no productivos.
     */
    public boolean isDevOrLocal() {
        String env = System.getenv("EASY_ENV");
        return env == null || env.equalsIgnoreCase("dev") || env.equalsIgnoreCase("local") ||
               isLocalhost(System.getenv("HOSTNAME"));
    }

    private boolean isLocalhost(String hostname) {
        return hostname == null || hostname.contains("localhost");
    }

    /**
     * Resuelve el dominio en el que se está ejecutando la aplicación.
     * Este valor será usado para ligar la licencia a un entorno específico.
     * 
     * Se espera que la variable de entorno APP_DOMAIN esté definida en producción.
     */
    public String resolveDomain() {
        String env = System.getenv("APP_DOMAIN");
        return (env != null && !env.isBlank()) ? env : "localhost";
    }
}
