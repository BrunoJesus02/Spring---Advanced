package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.model.output.GrupoModel;
import com.algaworks.algaworksapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoModelInputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoModel toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    public List<GrupoModel> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
