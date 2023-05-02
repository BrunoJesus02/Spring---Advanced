package com.algaworks.algaworksapi.jpa;

import com.algaworks.algaworksapi.AlgaworksApiApplication;
import com.algaworks.algaworksapi.domain.model.Cidade;
import com.algaworks.algaworksapi.domain.model.Estado;
import com.algaworks.algaworksapi.domain.repository.CidadeRepository;
import com.algaworks.algaworksapi.domain.repository.EstadoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaEstadoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworksApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

        List<Estado> estados = estadoRepository.listar();

        for (Estado estado : estados) {
            System.out.println(estado.getNome());
        }
    }
}
