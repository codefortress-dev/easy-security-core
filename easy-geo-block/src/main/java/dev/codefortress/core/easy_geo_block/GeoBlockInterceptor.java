package dev.codefortress.core.easy_geo_block;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * Interceptor que bloquea solicitudes según la ubicación geográfica
 * detectada a partir de la dirección IP del cliente.
 */
public class GeoBlockInterceptor implements HandlerInterceptor {

    private final GeoBlockService geoBlockService;

    public GeoBlockInterceptor(GeoBlockService geoBlockService) {
        this.geoBlockService = geoBlockService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String clientIp = extractClientIp(request);

        if (geoBlockService.isBlocked(clientIp)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain");
            response.getWriter().write(geoBlockService.getBlockMessage());
            return false;
        }

        return true;
    }

    /**
     * Obtiene la IP del cliente desde el encabezado o conexión directa.
     */
    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isBlank()) {
            return xfHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
