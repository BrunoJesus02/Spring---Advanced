package com.algaworks.algaworksapi.jpa;

import com.algaworks.algaworksapi.AlgaworksApiApplication;
import com.algaworks.algaworksapi.domain.model.FormaPagamento;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.algaworks.algaworksapi.domain.repository.FormaPagamentoRepository;
import com.algaworks.algaworksapi.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaFormaPagamentoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworksApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository restauranteRepository = applicationContext.getBean(FormaPagamentoRepository.class);

        List<FormaPagamento> formaPagamentos = restauranteRepository.listar();

        for (FormaPagamento f : formaPagamentos) {
            System.out.printf(f.getDescricao());
        }
    }
}
