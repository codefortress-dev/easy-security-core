package dev.codefortress.core.easy_context;

/**
 * Representa el tenant (cliente o inquilino) actual en el contexto de ejecución.
 * Esta clase permite identificar el propietario de los datos o solicitudes
 * en aplicaciones multitenant.
 *
 * Usado en módulos como:
 * - easy-data-toolkit (para separar datos por tenant)
 * - easy-auditing (para registrar qué tenant generó una acción)
 * - eventbridge (para enrutar eventos por tenant)
 */
public class TenantContext {

    private final String tenantId;

    /**
     * Construye un contexto de tenant con su identificador.
     *
     * @param tenantId identificador único del tenant (ej: empresa1, orgX, id-uuid)
     */
    public TenantContext(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Devuelve el identificador del tenant actual.
     */
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public String toString() {
        return "TenantContext{tenantId='" + tenantId + "'}";
    }
}
