package dev.codefortress.core.easy_geo_block;

import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Servicio que encapsula la lógica de decisión sobre
 * si una IP debe ser bloqueada según el país de origen.
 */
@Service
public class GeoBlockService {

    private final GeoLocationProvider locationProvider;
    private final GeoBlockProperties properties;

    public GeoBlockService(GeoLocationProvider provider, GeoBlockProperties props) {
        this.locationProvider = provider;
        this.properties = props;
    }

    /**
     * Verifica si una IP debe ser bloqueada.
     *
     * @param ip dirección IP a evaluar
     * @return true si debe bloquearse, false si se permite
     */
    public boolean isBlocked(String ip) {
        if (!properties.isEnabled()) return false;

        String countryCode = locationProvider.resolveCountryCode(ip);
        if (countryCode == null) return true;

        countryCode = countryCode.trim().toUpperCase(Locale.ROOT);
        return !properties.getAllowedCountries().contains(countryCode);
    }

    /**
     * Devuelve el código de país detectado.
     */
    public String getCountry(String ip) {
        return locationProvider.resolveCountryCode(ip);
    }

    /**
     * Devuelve el mensaje de bloqueo personalizado.
     */
    public String getBlockMessage() {
        return properties.getBlockMessage();
    }
}
