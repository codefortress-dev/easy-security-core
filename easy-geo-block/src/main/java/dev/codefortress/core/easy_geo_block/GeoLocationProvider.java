package dev.codefortress.core.easy_geo_block;

/**
 * Interfaz que define cómo obtener el código de país (ISO alpha-2)
 * a partir de una dirección IP.
 */
public interface GeoLocationProvider {

    /**
     * Resuelve el código de país (ej: "US", "CL") desde una IP.
     *
     * @param ip dirección IP pública
     * @return código de país o null si no se puede determinar
     */
    String resolveCountryCode(String ip);
}
