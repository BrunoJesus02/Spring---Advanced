package com.algaworks.algaworksapi.jpa;

import com.algaworks.algaworksapi.AlgaworksApiApplication;
import com.algaworks.algaworksapi.domain.model.Cozinha;
import com.algaworks.algaworksapi.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ExclusaoCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworksApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setId(1L);

        cozinhaRepository.remover(cozinha1);

    }
}
