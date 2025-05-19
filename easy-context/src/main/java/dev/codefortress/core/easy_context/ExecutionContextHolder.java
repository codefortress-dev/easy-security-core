package dev.codefortress.core.easy_context;

/**
 * Contenedor estático thread-safe del contexto de ejecución actual.
 * Permite acceder al usuario y al tenant actual desde cualquier punto
 * del código dentro del mismo hilo, sin necesidad de inyección.
 *
 * Utiliza {@link ThreadLocal} para mantener la información de forma segura
 * por hilo, lo cual es ideal para entornos web o asincrónicos controlados.
 *
 * Debe llamarse a {@code clearAll()} al finalizar una solicitud para evitar fugas de memoria.
 */
public class ExecutionContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();
    private static final ThreadLocal<TenantContext> tenantContext = new ThreadLocal<>();

    /**
     * Establece el contexto de usuario actual.
     */
    public static void setUser(UserContext user) {
        userContext.set(user);
    }

    /**
     * Obtiene el contexto de usuario actual.
     */
    public static UserContext getUser() {
        return userContext.get();
    }

    /**
     * Elimina el contexto de usuario actual del hilo.
     */
    public static void clearUser() {
        userContext.remove();
    }

    /**
     * Establece el contexto de tenant actual.
     */
    public static void setTenant(TenantContext tenant) {
        tenantContext.set(tenant);
    }

    /**
     * Obtiene el contexto de tenant actual.
     */
    public static TenantContext getTenant() {
        return tenantContext.get();
    }

    /**
     * Elimina el contexto de tenant actual del hilo.
     */
    public static void clearTenant() {
        tenantContext.remove();
    }

    /**
     * Limpia completamente el contexto del hilo (usuario y tenant).
     */
    public static void clearAll() {
        clearUser();
        clearTenant();
    }
}
