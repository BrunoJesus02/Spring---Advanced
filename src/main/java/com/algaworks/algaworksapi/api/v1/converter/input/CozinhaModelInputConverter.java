package com.algaworks.algaworksapi.api.v1.converter.input;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.controller.CozinhaController;
import com.algaworks.algaworksapi.api.v1.model.output.CozinhaModel;
import com.algaworks.algaworksapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CozinhaModelInputConverter extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGenerator linksGenerator;

    public CozinhaModelInputConverter() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(linksGenerator.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }

}
