package com.algaworks.algaworksapi.api.v2.converter.output;

import com.algaworks.algaworksapi.api.v1.model.input.CozinhaInput;
import com.algaworks.algaworksapi.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algaworksapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelOutputConverterV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInputV2 cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInputV2 cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}
