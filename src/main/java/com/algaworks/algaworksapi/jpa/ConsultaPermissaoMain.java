package com.algaworks.algaworksapi.jpa;

import com.algaworks.algaworksapi.AlgaworksApiApplication;
import com.algaworks.algaworksapi.domain.model.Cidade;
import com.algaworks.algaworksapi.domain.model.Permissao;
import com.algaworks.algaworksapi.domain.repository.CidadeRepository;
import com.algaworks.algaworksapi.domain.repository.PermissaoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaPermissaoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworksApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

        List<Permissao> permissoes = permissaoRepository.listar();

        for (Permissao permissao : permissoes) {
            System.out.println(permissao.getNome() + permissao.getDescricao());
        }
    }
}
