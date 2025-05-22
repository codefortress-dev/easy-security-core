package dev.codefortress.core.easy_rate_limit;

import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_context.common.DelegatingInterceptorRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties(RateLimitProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.rate-limit", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RateLimitAutoConfiguration implements WebMvcConfigurer {

    private final ApplicationContext context;

    public RateLimitAutoConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public RateLimitProperties validatedRateLimitProperties(RateLimitProperties props) {
        EasyConfigScanner.preload(RateLimitProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    }

    @Bean
    public RateLimitInterceptor rateLimitInterceptor(RateLimitProperties props) {
        return new RateLimitInterceptor(props);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        new DelegatingInterceptorRegistry()
            .addInterceptor(context.getBean(RateLimitInterceptor.class), 0)
            .applyTo(registry);
    }
}
