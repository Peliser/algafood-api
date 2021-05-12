package com.algaworks.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SMTPEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEnvioEmailService;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
        case FAKE:
            return new FakeEnvioEmailService();
        case SMTP:
            return new SMTPEnvioEmailService();
        case SANDBOX:
            return new SandboxEnvioEmailService();
        default:
            return null;
        }
    }

}