package dev.codefortress.core.easy_rate_limit;

/**
 * Permite saber desde cualquier parte del flujo si la solicitud actual
 * fue bloqueada o aceptada por el sistema de rate-limit.
 *
 * Utiliza ThreadLocal para asociar el estado a cada request.
 */
public class RateLimitContext {

    private static final ThreadLocal<RateLimitState> state = new ThreadLocal<>();

    /**
     * Define el estado de la solicitud actual.
     */
    public static void set(RateLimitState rateLimitState) {
        state.set(rateLimitState);
    }

    /**
     * Obtiene el estado de la solicitud actual.
     */
    public static RateLimitState get() {
        return state.get();
    }

    /**
     * Limpia el estado del hilo actual.
     */
    public static void clear() {
        state.remove();
    }
}
