package com.algaworks.algaworksapi.api.v1.converter.input;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.controller.GrupoController;
import com.algaworks.algaworksapi.api.v1.model.output.GrupoModel;
import com.algaworks.algaworksapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelInputConverter
        extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGenerator linksGenerator;

    public GrupoModelInputConverter() {
        super(GrupoController.class, GrupoModel.class);
    }

    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);

        grupoModel.add(linksGenerator.linkToGrupos("grupos"));

        grupoModel.add(linksGenerator.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(linksGenerator.linkToGrupos());
    }
}
