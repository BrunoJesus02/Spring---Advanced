package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.controller.CidadeController;
import com.algaworks.algaworksapi.api.controller.EstadoController;
import com.algaworks.algaworksapi.api.model.output.CidadeModel;
import com.algaworks.algaworksapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeModelInputConverter extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeModelInputConverter() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(linkTo(methodOn(CidadeController.class)
                .listar())
                .withRel("cidades"));
        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId()))
                .withSelfRel());

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
    }
}
