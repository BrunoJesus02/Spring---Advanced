package com.algaworks.algaworksapi.api.v2.converter.output;

import com.algaworks.algaworksapi.api.v1.model.input.CidadeInput;
import com.algaworks.algaworksapi.api.v2.model.input.CidadeInputV2;
import com.algaworks.algaworksapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelOutputConverterV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputV2 cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputV2 cidadeInput, Cidade cidade) {
        modelMapper.map(cidadeInput, cidade);
    }
}
