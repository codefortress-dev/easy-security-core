package dev.codefortress.core.easy_context.common;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Registro delegado que permite construir interceptores en módulos individuales
 * y luego agregarlos de forma centralizada y ordenada al InterceptorRegistry real.
 */
public class DelegatingInterceptorRegistry {

    private final List<Registration> registrations = new ArrayList<>();

    public DelegatingInterceptorRegistry addInterceptor(HandlerInterceptor interceptor, int order) {
        registrations.add(new Registration(interceptor, order));
        return this;
    }

    public void applyTo(InterceptorRegistry registry) {
        registrations.stream()
            .sorted((a, b) -> Integer.compare(a.order, b.order))
            .forEach(r -> registry.addInterceptor(r.interceptor).order(r.order));
    }

    private static class Registration {
        private final HandlerInterceptor interceptor;
        private final int order;

        public Registration(HandlerInterceptor interceptor, int order) {
            this.interceptor = interceptor;
            this.order = order;
        }
    }
}
