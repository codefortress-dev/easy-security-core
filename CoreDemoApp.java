package com.demo;

import dev.codefortress.core.easy_context.EasyExecutionContext;
import dev.codefortress.core.easy_licensing.LicenseCheckResult;
import dev.codefortress.core.easy_licensing.LicenseFingerprintProvider;
import dev.codefortress.core.easy_licensing.SecuritySuiteLicenseProperties;
import dev.codefortress.core.easy_licensing.TrialAwareLicenseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreDemoApp implements CommandLineRunner {

    @Autowired
    private TrialAwareLicenseValidator validator;

    @Autowired
    private SecuritySuiteLicenseProperties validatedSecuritySuiteLicenseProperties;

    @Autowired
    private LicenseFingerprintProvider fingerprintProvider;

    public static void main(String[] args) {
        SpringApplication.run(CoreDemoApp.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("==== PRUEBA DE LICENCIA ====");
        LicenseCheckResult result = validator.validate("usuario@demo.com", "secreta", "demo-project");
        System.out.println("Estado: " + result.getStatus());
        System.out.println("Mensaje: " + result.getMessage());

        System.out.println("==== PROPIEDADES DE CONFIGURACIÓN ====");
        System.out.println("KEY CONFIGURADA: " + validatedSecuritySuiteLicenseProperties.getKey());
        System.out.println("TIMESTAMP TRIAL: " + validatedSecuritySuiteLicenseProperties.getTrialStartTimestamp());

        System.out.println("==== FINGERPRINT ====");
        System.out.println("Huella actual: " + fingerprintProvider.getActiveFingerprint());

        System.out.println("==== CONTEXTO ====");
        System.out.println("Modo actual: " + EasyExecutionContext.getCurrentContext());

        System.out.println("==== FIN DE PRUEBAS ====");
    }
}
