package dev.codefortress.core.easy_captcha;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 * Servicio que encapsula la lógica para detectar bots
 * a partir del comportamiento en el formulario.
 */
@Service
public class CaptchaService {

    private final CaptchaProperties properties;

    public CaptchaService(CaptchaProperties properties) {
        this.properties = properties;
    }

    public CaptchaProperties getConfig() {
    return properties;
}

    /**
     * Determina si una solicitud parece sospechosa
     * (comportamiento automatizado).
     *
     * @param request petición HTTP
     * @return true si parece un bot
     */
    public boolean isSuspicious(HttpServletRequest request) {
        if (!properties.isEnabled()) return false;

        // Validación por tiempo de respuesta
        String startTimeStr = request.getParameter("_formStartTime");
        if (startTimeStr != null) {
            try {
                long startTime = Long.parseLong(startTimeStr);
                long duration = System.currentTimeMillis() - startTime;
                if (duration < properties.getMinResponseTimeMillis()) return true;
            } catch (NumberFormatException e) {
                return true;
            }
        } else {
            return true; // No se envió el timestamp
        }

        // Validación tipo honeypot
        if (properties.isHoneypotEnabled()) {
            String honeypot = request.getParameter(properties.getHoneypotField());
            if (honeypot != null && !honeypot.isBlank()) return true;
        }

        return false;
    }

    /**
     * Obtiene el mensaje personalizado a mostrar si es bloqueado.
     */
    public String getBlockMessage() {
        return properties.getBlockMessage();
    }
}
