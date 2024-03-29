package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.v1.converter.input.PermissaoModelInputConverter;
import com.algaworks.algaworksapi.api.v1.model.output.PermissaoModel;
import com.algaworks.algaworksapi.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algaworksapi.domain.model.Permissao;
import com.algaworks.algaworksapi.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoModelInputConverter permissaoModelInputConverter;

    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();

        return permissaoModelInputConverter.toCollectionModel(todasPermissoes);
    }
}
