package dev.codefortress.core.easy_context;

/**
 * Representa al usuario actual en el contexto de ejecución.
 * Esta clase es utilizada para compartir información de autenticación
 * entre distintos módulos de la aplicación de forma desacoplada.
 *
 * Esta estructura puede ser utilizada para:
 * - Auditoría de acciones
 * - Resolución de roles o permisos
 * - Registro de trazabilidad
 */
public class UserContext {

    private final String username;
    private final String email;
    private final String[] roles;

    /**
     * Construye un nuevo contexto de usuario con datos básicos.
     *
     * @param username nombre de usuario (ej: login o identificador)
     * @param email correo electrónico asociado
     * @param roles roles asociados al usuario (ej: ADMIN, USER)
     */
    public UserContext(String username, String email, String[] roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    /**
     * Devuelve el nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Devuelve el email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Devuelve los roles asociados al usuario.
     */
    public String[] getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + String.join(",", roles) +
                '}';
    }
}
