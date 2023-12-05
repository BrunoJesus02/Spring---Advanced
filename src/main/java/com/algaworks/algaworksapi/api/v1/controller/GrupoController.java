package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algaworksapi.api.v1.converter.input.GrupoModelInputConverter;
import com.algaworks.algaworksapi.api.v1.converter.output.GrupoModelOutputConverter;
import com.algaworks.algaworksapi.api.v1.model.input.GrupoInput;
import com.algaworks.algaworksapi.api.v1.model.output.GrupoModel;
import com.algaworks.algaworksapi.domain.model.Grupo;
import com.algaworks.algaworksapi.domain.repository.GrupoRepository;
import com.algaworks.algaworksapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoModelOutputConverter grupoOutputConverter;

    @Autowired
    private GrupoModelInputConverter grupoInputConverter;

    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        return grupoInputConverter.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        return grupoInputConverter.toModel(grupoService.buscarOuFalhar(grupoId));
    }

    @PostMapping
    public GrupoModel adicionar(@RequestBody GrupoInput grupoInput) {
        Grupo grupo = grupoOutputConverter.toDomainObject(grupoInput);
        return grupoInputConverter.toModel(grupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@RequestBody GrupoInput grupoInput, @PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        grupoOutputConverter.copyToDomainObject(grupoInput, grupo);

        return grupoInputConverter.toModel(grupoService.salvar(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);
    }
}
