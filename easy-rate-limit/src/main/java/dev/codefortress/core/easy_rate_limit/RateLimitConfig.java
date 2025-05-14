package dev.codefortress.core.easy_rate_limit;

import dev.codefortress.configui.AutoLoadable;
import dev.codefortress.configui.EasyConfigProperty;

@AutoLoadable
public class RateLimitConfig {

    @EasyConfigProperty(
        value = "rate.limit.max-requests",
        description = "Cantidad máxima de solicitudes permitidas por IP dentro del intervalo")
    private int maxRequests = 100;

    @EasyConfigProperty(
        value = "rate.limit.interval-ms",
        description = "Intervalo de tiempo en milisegundos para reiniciar el conteo")
    private long intervalMs = 60000;

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getIntervalMs() {
        return intervalMs;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public void setIntervalMs(long intervalMs) {
        this.intervalMs = intervalMs;
    }
}
