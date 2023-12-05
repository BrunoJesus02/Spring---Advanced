package com.algaworks.algaworksapi.api.v2.controller;

import com.algaworks.algaworksapi.api.ResourceUriHelper;
import com.algaworks.algaworksapi.api.v2.converter.input.CidadeModelInputConverterV2;
import com.algaworks.algaworksapi.api.v2.converter.output.CidadeModelOutputConverterV2;
import com.algaworks.algaworksapi.api.v2.model.input.CidadeInputV2;
import com.algaworks.algaworksapi.api.v2.model.output.CidadeModelV2;
import com.algaworks.algaworksapi.core.web.AlgaMidiaTypes;
import com.algaworks.algaworksapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.algaworks.algaworksapi.domain.model.Cidade;
import com.algaworks.algaworksapi.domain.repository.CidadeRepository;
import com.algaworks.algaworksapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cidades", produces = AlgaMidiaTypes.V2_APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelInputConverterV2 cidadeInputConverter;

    @Autowired
    private CidadeModelOutputConverterV2 cidadeOutputConverter;

    @GetMapping
    public CollectionModel<CidadeModelV2> listar() {
        return cidadeInputConverter.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeModelV2 buscar(@PathVariable Long cidadeId) {
        return cidadeInputConverter.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeOutputConverter.toDomainObject(cidadeInput);

            CidadeModelV2 cidadeModel = cidadeInputConverter.toModel(cadastroCidade.salvar(cidade));
            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getIdCidade());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModelV2 atualizar(@PathVariable Long cidadeId,
                                 @RequestBody @Valid CidadeInputV2 cidadeInput) {
        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

        cidadeOutputConverter.copyToDomainObject(cidadeInput, cidadeAtual);

        try {
            return cidadeInputConverter.toModel(cadastroCidade.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    public void remover(@PathVariable Long cidadeId) {
            cadastroCidade.excluir(cidadeId);
    }
}
