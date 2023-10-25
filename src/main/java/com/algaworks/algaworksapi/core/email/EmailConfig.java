package com.algaworks.algaworksapi.core.email;

import com.algaworks.algaworksapi.domain.service.EnvioEmailService;
import com.algaworks.algaworksapi.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algaworksapi.infrastructure.service.email.SandBoxEnvioEmailService;
import com.algaworks.algaworksapi.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandBoxEnvioEmailService();
            default:
                return null;
        }
    }
}
