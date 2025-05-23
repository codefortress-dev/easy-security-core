package dev.codefortress.core.easy_licensing;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Cliente HTTP para validar licencias en el servidor comercial remoto.
 * Realiza una llamada POST con el producto, clave y dominio actual, y espera
 * como respuesta una licencia firmada.
 *
 * Este cliente no debe inyectarse automáticamente; se recomienda instanciarlo
 * desde LicensingAutoConfiguration o LicenseValidatorFactory.
 */
public class LicenseRemoteValidator {

    private final WebClient client;

    public LicenseRemoteValidator() {
        this.client = WebClient.builder()
                .baseUrl("https://license.codefortress.dev/api") // puede leerse de propiedades si lo deseas
                .build();
    }

    /**
     * Envía la solicitud de validación de licencia al servidor remoto.
     *
     * @param product identificador del módulo comercial (ej: "security-suite")
     * @param key     clave ingresada por el usuario
     * @param domain  dominio desde donde se activa
     * @return LicenseInfo si es válida, o null si la validación falla
     */
    public LicenseInfo validate(String product, String key, String domain) {
        try {
            return client.post()
                    .uri("/validate")
                    .bodyValue(new ValidationRequest(product, key, domain))
                    .retrieve()
                    .bodyToMono(LicenseInfo.class)
                    .block(); // sincronización temporal
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    /**
     * Cuerpo de la solicitud enviada al backend de licencias.
     */
    private record ValidationRequest(String product, String key, String domain) {}
}
