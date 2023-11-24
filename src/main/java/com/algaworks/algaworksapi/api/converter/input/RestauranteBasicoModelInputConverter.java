package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.LinksGenerator;
import com.algaworks.algaworksapi.api.controller.RestauranteController;
import com.algaworks.algaworksapi.api.model.output.RestauranteBasicoModel;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelInputConverter
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGenerator linksGenerator;

    public RestauranteBasicoModelInputConverter() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }

    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(linksGenerator.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(
                linksGenerator.linkToCozinha(restaurante.getCozinha().getId()));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(linksGenerator.linkToRestaurantes());
    }
}
