package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.converter.input.FormaPagamentoInputConverter;
import com.algaworks.algaworksapi.api.v1.model.output.FormaPagamentoModel;
import com.algaworks.algaworksapi.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.algaworks.algaworksapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private FormaPagamentoInputConverter formaPagamentoInputConverter;

    @Autowired
    private LinksGenerator linksGenerator;

    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        CollectionModel<FormaPagamentoModel> formaPagamentoModels =
                formaPagamentoInputConverter.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(linksGenerator.linkToRestauranteFormasPagamento(restauranteId))
                .add(linksGenerator.linkToRestauranteFormaPagamentoAssociar(restauranteId, "associar"));

        formaPagamentoModels.getContent().forEach(formaPagamentoModel -> {
            formaPagamentoModels.add(linksGenerator
                    .linkToRestauranteFormaPagamentoDesassociacao(restauranteId, formaPagamentoModel.getId(), "desassociar"));
        });

        return formaPagamentoModels;
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.asassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

}
