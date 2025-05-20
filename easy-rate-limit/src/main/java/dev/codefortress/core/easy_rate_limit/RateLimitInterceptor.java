package dev.codefortress.core.easy_rate_limit;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interceptor que evalúa si una solicitud debe ser bloqueada
 * según las reglas de rate limiting definidas en la configuración.
 *
 * Este interceptor se ejecuta antes de cada request.
 */
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitProperties props;
    private final Map<String, RequestTracker> requestMap = new ConcurrentHashMap<>();

    public RateLimitInterceptor(RateLimitProperties props) {
        this.props = props;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        RateLimitContext.clear();

        if (!props.isEnabled()) {
            RateLimitContext.set(RateLimitState.ALLOWED);
            return true;
        }

        String ip = request.getRemoteAddr();
        long now = Instant.now().getEpochSecond();

        RequestTracker tracker = requestMap.computeIfAbsent(ip, k -> new RequestTracker());

        if (tracker.isBlocked(now, props)) {
            RateLimitContext.set(RateLimitState.BLOCKED);
            response.setStatus(429); // Too Many Requests
            response.getWriter().write("Demasiadas solicitudes. Intenta nuevamente más tarde.");
            return false;
        }

        tracker.register(now, props);
        RateLimitContext.set(RateLimitState.ALLOWED);
        return true;
    }

    /**
     * Clase interna que mantiene el estado por IP.
     */
    private static class RequestTracker {
        private long windowStart = 0;
        private int count = 0;

        public boolean isBlocked(long now, RateLimitProperties props) {
            if (now - windowStart >= props.getTimeWindowSeconds()) {
                windowStart = now;
                count = 0;
            }
            return count >= props.getMaxRequests();
        }

        public void register(long now, RateLimitProperties props) {
            if (now - windowStart >= props.getTimeWindowSeconds()) {
                windowStart = now;
                count = 0;
            }
            count++;
        }
    }
}
