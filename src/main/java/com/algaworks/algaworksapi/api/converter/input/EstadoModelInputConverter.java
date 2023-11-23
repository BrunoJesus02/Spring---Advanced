package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.controller.CidadeController;
import com.algaworks.algaworksapi.api.controller.EstadoController;
import com.algaworks.algaworksapi.api.model.output.EstadoModel;
import com.algaworks.algaworksapi.domain.model.Estado;
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
public class EstadoModelInputConverter extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModelInputConverter() {
        super(EstadoController.class, EstadoModel.class);
    }

    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);

        modelMapper.map(estado, estadoModel);

        estadoModel.add(linkTo(EstadoController.class).withRel("estados"));

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(EstadoController.class).withSelfRel());
    }
}
