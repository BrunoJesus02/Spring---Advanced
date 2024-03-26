package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.ResourceUriHelper;
import com.algaworks.algaworksapi.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algaworksapi.api.v1.converter.input.CidadeModelInputConverter;
import com.algaworks.algaworksapi.api.v1.converter.output.CidadeModelOutputConverter;
import com.algaworks.algaworksapi.api.v1.model.input.CidadeInput;
import com.algaworks.algaworksapi.api.v1.model.output.CidadeModel;
import com.algaworks.algaworksapi.core.security.CheckSecurity;
import com.algaworks.algaworksapi.core.web.AlgaMidiaTypes;
import com.algaworks.algaworksapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.algaworks.algaworksapi.domain.model.Cidade;
import com.algaworks.algaworksapi.domain.repository.CidadeRepository;
import com.algaworks.algaworksapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelInputConverter cidadeInputConverter;

    @Autowired
    private CidadeModelOutputConverter cidadeOutputConverter;

    @CheckSecurity.Cidades.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<CidadeModel> listar() {
        return cidadeInputConverter.toCollectionModel(cidadeRepository.findAll());
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        return cidadeInputConverter.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeOutputConverter.toDomainObject(cidadeInput);

            CidadeModel cidadeModel = cidadeInputConverter.toModel(cadastroCidade.salvar(cidade));
            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId,
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

        cidadeOutputConverter.copyToDomainObject(cidadeInput, cidadeAtual);

        try {
            return cidadeInputConverter.toModel(cadastroCidade.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("/{cidadeId}")
    public void remover(@PathVariable Long cidadeId) {
            cadastroCidade.excluir(cidadeId);
    }
}
