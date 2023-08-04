package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.model.PermissaoModel;
import com.algaworks.algaworksapi.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoModelInputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollectionModel(Collection<Permissao> permissaos) {
        return permissaos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
