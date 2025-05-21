package dev.codefortress.core.easy_geo_block;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Implementación temporal que utiliza el servicio público ipapi.co
 * para resolver el país de una dirección IP.
 *
 * Útil para pruebas o MVP, pero no se recomienda para producción.
 */
@Component
public class IpApiGeoLocationProvider implements GeoLocationProvider {

    private final WebClient webClient;

    public IpApiGeoLocationProvider() {
        this.webClient = WebClient.builder()
                .baseUrl("https://ipapi.co")
                .build();
    }

    @Override
    public String resolveCountryCode(String ip) {
        try {
            return webClient.get()
                    .uri("/{ip}/country/", ip)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(e -> Mono.empty())
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}
