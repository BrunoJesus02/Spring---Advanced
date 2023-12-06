package com.algaworks.algaworksapi.api.v2.converter.input;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.controller.CozinhaController;
import com.algaworks.algaworksapi.api.v1.model.output.CozinhaModel;
import com.algaworks.algaworksapi.api.v2.LinksGeneratorV2;
import com.algaworks.algaworksapi.api.v2.controller.CozinhaControllerV2;
import com.algaworks.algaworksapi.api.v2.model.output.CozinhaModelV2;
import com.algaworks.algaworksapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelInputConverterV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGeneratorV2 linksGenerator;

    public CozinhaModelInputConverterV2() {
        super(CozinhaControllerV2.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha cozinha) {
        CozinhaModelV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(linksGenerator.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }

}
