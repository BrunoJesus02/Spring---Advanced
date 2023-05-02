package com.algaworks.algaworksapi.jpa;

import com.algaworks.algaworksapi.AlgaworksApiApplication;
import com.algaworks.algaworksapi.domain.model.Cidade;
import com.algaworks.algaworksapi.domain.model.FormaPagamento;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.algaworks.algaworksapi.domain.repository.CidadeRepository;
import com.algaworks.algaworksapi.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCidadeMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworksApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);

        List<Cidade> cidades = cidadeRepository.listar();

        for (Cidade cidade : cidades) {
            System.out.printf("%s - %s\n", cidade.getNome(), cidade.getEstado().getNome());
        }
    }
}
