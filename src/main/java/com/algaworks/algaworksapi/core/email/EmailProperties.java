package com.algaworks.algaworksapi.core.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    private Sandbox sandbox = new Sandbox();

    private Implementacao impl = Implementacao.FAKE;

    public enum Implementacao {
        SMTP, FAKE, SANDBOX
    }

    @Setter
    @Getter
    public class Sandbox {
        private String destinatario;
    }
}
