package com.algaworks.algaworksapi.api.v2.controller;

import com.algaworks.algaworksapi.api.v2.converter.input.CozinhaModelInputConverterV2;
import com.algaworks.algaworksapi.api.v2.converter.output.CozinhaModelOutputConverterV2;
import com.algaworks.algaworksapi.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algaworksapi.api.v2.model.output.CozinhaModelV2;
import com.algaworks.algaworksapi.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.algaworks.algaworksapi.domain.model.Cozinha;
import com.algaworks.algaworksapi.domain.repository.CozinhaRepository;
import com.algaworks.algaworksapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v2/cozinhas")
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelInputConverterV2 cozinhaInputConverter;

    @Autowired
    private CozinhaModelOutputConverterV2 cozinhaOutputConverter;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaInputConverter);
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaModelV2 buscar(@PathVariable Long cozinhaId) {
        return cozinhaInputConverter.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        Cozinha cozinha = cozinhaOutputConverter.toDomainObject(cozinhaInput);

        return cozinhaInputConverter.toModel(cadastroCozinha.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaModelV2 atualizar(@PathVariable Long cozinhaId, @RequestBody CozinhaInputV2 cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);

        cozinhaOutputConverter.copyToDomainObject(cozinhaInput, cozinhaAtual);

        return cozinhaInputConverter.toModel(cadastroCozinha.salvar(cozinhaAtual));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinha.excluir(cozinhaId);
    }
}
