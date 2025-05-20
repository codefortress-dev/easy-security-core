package dev.codefortress.core.easy_licensing;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Cliente HTTP para validar licencias en el servidor comercial remoto.
 * Realiza una llamada POST con el producto, clave y dominio actual, y espera
 * como respuesta una licencia firmada.
 *
 * Este cliente no debe inyectarse automáticamente; debe registrarse
 * desde LicensingAutoConfiguration.
 */
public class LicenseRemoteValidator {

    private final WebClient client;

    public LicenseRemoteValidator() {
        this.client = WebClient.builder()
                .baseUrl("https://license.codefortress.dev/api")
                .build();
    }

    /**
     * Envía la solicitud de validación de licencia al servidor.
     *
     * @param product identificador del producto (ej: security-suite)
     * @param key clave de activación
     * @param domain dominio desde el cual se activa
     * @return objeto LicenseInfo si es válida, o null si falló
     */
    public LicenseInfo validate(String product, String key, String domain) {
        try {
            return client.post()
                    .uri("/validate")
                    .bodyValue(new ValidationRequest(product, key, domain))
                    .retrieve()
                    .bodyToMono(LicenseInfo.class)
                    .block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    /**
     * Representa el cuerpo de la solicitud de validación de licencia.
     */
    private record ValidationRequest(String product, String key, String domain) {}
}
