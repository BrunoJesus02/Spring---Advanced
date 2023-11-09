package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.PermissaoModelInputConverter;
import com.algaworks.algaworksapi.api.model.output.PermissaoModel;
import com.algaworks.algaworksapi.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algaworksapi.domain.model.Grupo;
import com.algaworks.algaworksapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private PermissaoModelInputConverter permissaoModelInputConverter;

    @GetMapping()
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        return permissaoModelInputConverter.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }
}
