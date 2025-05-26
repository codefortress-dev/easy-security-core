package dev.codefortress.core.easy_licensing;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Cliente HTTP para validar licencias en el servidor comercial remoto.
 * Devuelve null si la conexión o la respuesta fallan.
 */
public class LicenseRemoteValidator {

    private final WebClient client;

    public LicenseRemoteValidator() {
        this.client = WebClient.builder()
                .baseUrl("https://license.codefortress.dev/api") // futuro backend comercial
                .build();
    }

    public LicenseInfo validate(String product, String key, String domain) {
        try {
            return client.post()
                    .uri("/validate")
                    .bodyValue(new ValidationRequest(product, key, domain))
                    .retrieve()
                    .bodyToMono(LicenseInfo.class)
                    .block();
        } catch (WebClientResponseException | WebClientRequestException e) {
            return null; // No rompe la aplicación
        }
    }

    private record ValidationRequest(String product, String key, String domain) {}
}
