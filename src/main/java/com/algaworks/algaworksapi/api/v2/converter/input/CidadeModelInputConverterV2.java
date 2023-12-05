package com.algaworks.algaworksapi.api.v2.converter.input;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.controller.CidadeController;
import com.algaworks.algaworksapi.api.v1.model.output.CidadeModel;
import com.algaworks.algaworksapi.api.v2.controller.CidadeControllerV2;
import com.algaworks.algaworksapi.api.v2.model.output.CidadeModelV2;
import com.algaworks.algaworksapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelInputConverterV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGenerator linksGenerator;

    public CidadeModelInputConverterV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }

    @Override
    public CidadeModelV2 toModel(Cidade cidade) {
        CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(linksGenerator.linkToCidades("cidades"));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linksGenerator.linkToCidades());
    }
}
