package dev.codefortress.core.easy_security_core_jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Utilitario para la creación y validación de tokens JWT firmados con HS256.
 * Esta clase no depende de Spring Security, lo que permite utilizarla en
 * diferentes módulos como easy-security-suite, easy-gateway-suite, etc.
 */
public class JwtTokenUtil {

    private final Key secretKey;
    private final long expirationSeconds;

    /**
     * Crea una nueva instancia del utilitario con la clave secreta y tiempo de expiración.
     *
     * @param secret clave secreta en texto plano
     * @param expirationSeconds duración del token en segundos
     */
    public JwtTokenUtil(String secret, long expirationSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationSeconds = expirationSeconds;
    }

    /**
     * Genera un token JWT con un sujeto y claims personalizados.
     *
     * @param subject sujeto del token (ej: nombre de usuario)
     * @param claims mapa de claims personalizados (ej: roles, tenant)
     * @return token JWT firmado
     */
    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Parsea un token JWT y devuelve sus claims si es válido.
     *
     * @param token el token JWT a validar
     * @return claims del token
     * @throws JwtException si el token es inválido o está expirado
     */
    public Claims parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Verifica si un token JWT es válido.
     *
     * @param token el token JWT a verificar
     * @return true si el token es válido, false si es inválido o expirado
     */
    public boolean isValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
