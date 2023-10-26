package org.algaworks.algafood.client.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestauranteInput {

    private String nome;
    private BigDecimal taxaFrete;

    private CozinhaIdInput cozinha;
    private EnderecoInput endereco;
}