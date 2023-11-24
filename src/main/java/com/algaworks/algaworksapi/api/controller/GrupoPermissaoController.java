package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.LinksGenerator;
import com.algaworks.algaworksapi.api.converter.input.PermissaoModelInputConverter;
import com.algaworks.algaworksapi.api.model.output.PermissaoModel;
import com.algaworks.algaworksapi.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algaworksapi.domain.model.Grupo;
import com.algaworks.algaworksapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private PermissaoModelInputConverter permissaoModelInputConverter;

    @Autowired
    private LinksGenerator linksGenerator;

    @GetMapping()
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        CollectionModel<PermissaoModel> permissoesModel
                = permissaoModelInputConverter.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(linksGenerator.linkToGrupoPermissoes(grupoId))
                .add(linksGenerator.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesModel.getContent().forEach(permissaoModel -> {
            permissaoModel.add(linksGenerator.linkToGrupoPermissaoDesassociacao(
                    grupoId, permissaoModel.getId(), "desassociar"));
        });

        return permissoesModel;
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}
