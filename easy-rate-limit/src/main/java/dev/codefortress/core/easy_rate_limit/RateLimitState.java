package dev.codefortress.core.easy_rate_limit;

/**
 * Estado del sistema de rate limit para una solicitud.
 * 
 * Puede ser:
 * - ALLOWED: la solicitud pasa
 * - BLOCKED: la solicitud excedió el límite
 */
public enum RateLimitState {
    ALLOWED,
    BLOCKED
}
