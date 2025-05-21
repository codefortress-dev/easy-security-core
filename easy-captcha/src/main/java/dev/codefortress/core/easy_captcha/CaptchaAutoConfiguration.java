package dev.codefortress.core.easy_captcha;

import dev.codefortress.core.easy_config_ui.ConfigurationValidator;
import dev.codefortress.core.easy_config_ui.EasyConfigScanner;
import dev.codefortress.core.easy_licensing.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties({CaptchaProperties.class, SecuritySuiteLicenseProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "easy.captcha", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CaptchaAutoConfiguration implements WebMvcConfigurer {

    private final CaptchaInterceptor captchaInterceptor;

    public CaptchaAutoConfiguration(CaptchaInterceptor captchaInterceptor) {
        this.captchaInterceptor = captchaInterceptor;
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
        SecuritySuiteLicenseProperties licenseProps,
        LicenseValidator validator,
        LicenseEnvironmentResolver environmentResolver
    ) {
        LicenseCheckResult result = validator.validate(
            licenseProps.getProduct(),
            licenseProps.getKey(),
            environmentResolver.resolveDomain()
        );
        if (!result.isValid()) {
            throw new LicenseException("Captcha está disponible solo con licencia activa.");
        }

        return new CaptchaInterceptor(service, props.getProtectedPaths());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(captchaInterceptor).order(2);
    }
}
