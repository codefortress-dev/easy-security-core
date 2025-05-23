package dev.codefortress.core.easy_captcha;

import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_licensing.*;
import dev.codefortress.core.easy_context.common.DelegatingInterceptorRegistry;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguración del módulo Captcha silencioso de protección contra bots.
 */
@AutoConfiguration
@EnableConfigurationProperties({CaptchaProperties.class, ModuleLicenseProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.captcha", name = "enabled", havingValue = "true")
public class CaptchaAutoConfiguration {

    private final ApplicationContext context;

    public CaptchaAutoConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public CaptchaProperties validatedCaptchaProperties(CaptchaProperties props) {
        EasyConfigScanner.preload(CaptchaProperties.class);
        ConfigurationValidator.validate(props);
        return props;
    }

    @Bean
    public CaptchaService captchaService(CaptchaProperties props) {
        return new CaptchaService(props);
    }

    @Bean
    public CaptchaInterceptor captchaInterceptor(
        CaptchaService service,
        CaptchaProperties props,
        ModuleLicenseProperties licenseProps,
        LicenseValidator validator,
        LicenseEnvironmentResolver environmentResolver
    ) {
        LicenseCheckResult result = validator.validate(
            licenseProps.getProduct(),
            licenseProps.getKey(),
            environmentResolver.resolveDomain()
        );
        if (!result.isValid() && !result.isTrial()) {
            throw new LicenseException("Captcha está disponible solo con licencia activa.");
        }

        return new CaptchaInterceptor(service, props.getProtectedPaths());
    }

    @Bean
    public Object captchaInterceptorRegistration(
        DelegatingInterceptorRegistry registry,
        CaptchaInterceptor interceptor
    ) {
        registry.addInterceptor(interceptor,2);
        return new Object(); // placeholder para forzar el registro sin errores
    }
}
